function draw(pointsList, r) {
    var points = pointsList;
    drawCanvas(points, r);
}

const xAxisLabel = "X";
const yAxisLabel = "Y";

let xAxisScale;
let yAxisScale;

function _draw() {
    const canvas = document.getElementById("canvasDisplayOnly");
    if (canvas.getContext) {
        let context = canvas.getContext("2d");
        context.fillStyle = 'white';
        context.fillRect(0, 0, canvas.width, canvas.height);

        context.fillStyle = "black";
        context.strokeStyle = "black";

        // Определяем размеры рисунка
        let canvasWidth = canvas.width;
        let canvasHeight = canvas.height;

        xAxisScale = canvasWidth / 10;
        yAxisScale = canvasHeight / 10;

        // Определяем начальную точку
        let originX = canvasWidth / 2;
        let originY = canvasHeight / 2;

        // Рисуем ось x
        context.beginPath();
        context.moveTo(0, originY);
        context.lineTo(canvasWidth, originY);
        context.stroke();

        // Рисуем ось y
        context.beginPath();
        context.moveTo(originX, 0);
        context.lineTo(originX, canvasHeight);
        context.stroke();

        // Обозначаем значения на осях
        context.fontFamily = "Open Sans, sans-serif";
        let fontArgs = context.font.split(' ');
        let newSize = '14px';
        context.font = newSize + ' ' + fontArgs[fontArgs.length - 1];
        context.fillText(xAxisLabel, canvas.width - 15, canvas.height / 2 - 5);
        context.fillText(yAxisLabel, canvas.width / 2 + 5, 15);

        // Обозначаем метки на осях
        for (let i = -canvas.width / 2; i < canvas.width / 2; i += xAxisScale) {
            let scalePos = axesToCanvasCoordinates(i, 0, canvas);
            context.beginPath();
            context.moveTo(scalePos.x, scalePos.y - 5);
            context.lineTo(scalePos.x, scalePos.y + 5);
            context.stroke();
            context.fillText(rescaleXAxesCoordinate(i), scalePos.x - 10, scalePos.y + 20);
        }

        for (let j = -canvas.height / 2; j < canvas.height / 2; j += yAxisScale) {
            let scalePos = axesToCanvasCoordinates(0, j, canvas);
            context.beginPath();
            context.moveTo(scalePos.x - 5, scalePos.y);
            context.lineTo(scalePos.x + 5, scalePos.y);
            context.stroke();
            context.fillText(rescaleYAxesCoordinate(j), scalePos.x + 10, scalePos.y + 5);
        }
    }
}
function axesToCanvasCoordinates(xAxes, yAxes, canvas) {
    let originX = canvas.width / 2;
    let originY = canvas.height / 2;

    let canvasX = originX + xAxes;
    let canvasY = originY - yAxes;

    return { x: canvasX, y: canvasY };
}
function rescaleXAxesCoordinate(coordinate) {
    return coordinate / xAxisScale;
}

function rescaleYAxesCoordinate(coordinate) {
    return coordinate / yAxisScale;
}

function scaleXAxesCoordinate(coordinate) {
    return coordinate * xAxisScale;
}

function scaleYAxesCoordinate(coordinate) {
    return coordinate * yAxisScale;
}

function drawCanvas(points, r) {
    const canvas = document.getElementById("canvasDisplayOnly");
    if (canvas.getContext) {
        // очищаем поле для рисунка
        let context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.width, canvas.height);
        _draw();

        let startPointInAxes = {x: 0, y: 0};
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);


        // рисуем прямоугольник во 2 четверти:
        let endPointInAxes = {x: -(r/2), y: r};
        let endScaledPointInAxes = {
            x: scaleXAxesCoordinate(endPointInAxes.x),
            y: scaleYAxesCoordinate(endPointInAxes.y)
        };

        context.fillStyle = "rgb(51, 153, 255, 0.5)";
        context.beginPath();
        context.fillRect(startPointInCanvas.x, startPointInCanvas.y, endScaledPointInAxes.x, -endScaledPointInAxes.y);

        // рисуем треугольник в 4 четверти:
        let secondTrianglePointInAxes = {x: r, y: 0};
        let thirdTrianglePointInAxes = {x: 0, y: -r};
        drawTriangle(context, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes);

        // рисуем часть 1/4 круга в 3 четверти
        let calculatedRadius = scaleXAxesCoordinate(r/2);

        context.beginPath();
        context.arc(startPointInCanvas.x, startPointInCanvas.y, calculatedRadius, Math.PI, Math.PI / 2, true);
        context.fill();

        // рисуем недостающий треугольник до 1/4 круга в 3 четверти
        let secondTrianglePointInAxesM = {x: -(r/2), y: 0};
        let thirdTrianglePointInAxesM = {x: 0, y: -(r/2)};
        drawTriangle(context, startPointInAxes, secondTrianglePointInAxesM, thirdTrianglePointInAxesM);

    points.forEach((e) => {
        drawPoint(e.x/e.r*r,e.y/e.r*r,r,e.result);
    })


    canvas.onmousedown = function (event) {
        const rect = canvas.getBoundingClientRect();
        const mouseX = event.clientX - rect.left;
        const mouseY = event.clientY - rect.top;

        // Преобразование координаты canvas в координаты осей
        const axesCoordinates = canvasToAxesCoordinates(mouseX, mouseY, canvas);

        // Получение текущее значение R из HTML-элемента
        const rInput = document.querySelector("[id='j_idt10:R_input']");
        const currentR = rInput ? rInput.value : undefined;

        if (currentR === null || currentR === undefined) {
            alert("Please enter a valid value for R.");
            return; // прерывание
        }

        // Выведите значения в консоль
        console.log("Кликнуто по (x, y, R):", axesCoordinates.x, axesCoordinates.y, currentR);

        sendPointData(axesCoordinates.x, axesCoordinates.y, currentR);
    }
}
}
function drawTriangle (ctx, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes) {
    const canvas = document.getElementById("canvasDisplayOnly");
    if (canvas.getContext) {
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);
        let secondScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(secondTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(secondTrianglePointInAxes.y)
        }
        let thirdScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(thirdTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(thirdTrianglePointInAxes.y)
        };
        let secondTrianglePointInCanvas = axesToCanvasCoordinates
        (secondScaledTrianglePointInAxes.x, secondScaledTrianglePointInAxes.y, canvas);
        let thirdScaledTrianglePointInCanvas = axesToCanvasCoordinates
        (thirdScaledTrianglePointInAxes.x, thirdScaledTrianglePointInAxes.y, canvas);

        ctx.beginPath();
        ctx.moveTo(startPointInCanvas.x, startPointInCanvas.y);
        ctx.lineTo(secondTrianglePointInCanvas.x, secondTrianglePointInCanvas.y);
        ctx.lineTo(thirdScaledTrianglePointInCanvas.x, thirdScaledTrianglePointInCanvas.y);
        ctx.fill();


    }
}
function drawPoint(x, y, r, result) {
    const canvas = document.getElementById("canvasDisplayOnly");
    console.log("I'm in draw")
    if (canvas.getContext) {
        let context = canvas.getContext("2d");
        const pointSize = 4;

        let scaledPoint = {x: scaleXAxesCoordinate(x), y: scaleYAxesCoordinate(y)};
        let pointOnCanvas = axesToCanvasCoordinates(scaledPoint.x, scaledPoint.y, canvas);

        if(result === true || result === "true") {
            context.fillStyle = "rgb(0, 255, 0)"
        } else {
            context.fillStyle = "rgb(255, 0, 0)";
        }
        context.beginPath();
        context.fillRect(pointOnCanvas.x - pointSize / 2, pointOnCanvas.y - pointSize / 2, pointSize, pointSize);
    }
}
function sendPointData(x, y, r) {
    // Вызов удаленной команды для отправки данных в управляемый бин
    addPoint([{name:'x', value:x}, {name:'y', value:y}, {name:'r', value:r}]);
}
function canvasToAxesCoordinates(canvasX, canvasY, canvas) {
    let originX = canvas.width / 2;
    let originY = canvas.height / 2;

    let axesX = canvasX - originX;
    let axesY = originY - canvasY;

    return { x: rescaleXAxesCoordinate(axesX), y: rescaleYAxesCoordinate(axesY) };
}