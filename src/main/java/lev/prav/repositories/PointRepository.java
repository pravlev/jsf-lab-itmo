package lev.prav.repositories;

import com.google.gson.Gson;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lev.prav.services.AreaCheckInt;
import lev.prav.model.Points;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SessionScoped
@Named(value = "PointRepository")
public class PointRepository implements Serializable {
    private String errorText;

    public void clearErrorText() {
        errorText = "";
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    //    private final static EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    @Inject
    AreaCheckInt areaCheck;
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;
    public List<Points> getPoints() {
//        EntityManager entityManager = factory.createEntityManager();
        try {
            if (!"Данные успешно удалены".equals(errorText)) {
                errorText = "";
            }
            return entityManager.createQuery("select p from Points p", Points.class).getResultList();
        } catch (Throwable e){
            errorText = "Ошибка прдключениня к базе данных ";
            return List.of();
        }
    }

    @Transactional
    public Points addPoint(Points point) {
        validate(point);
//        EntityManager entityManager = factory.createEntityManager();
        try {
            errorText = "";
            point.setId(null);
            areaCheck.CheckHit(point);
            entityManager.persist(point);
            entityManager.flush();
        } catch (Throwable e){
            errorText = "Ошибка прдключениня к базе данных ";
            return null;
        }
        return point;
    }

    @Transactional
    public void clearTable() {
        try {
            errorText = "Данные успешно удалены";
            entityManager.createQuery("delete from Points").executeUpdate();
        } catch (Throwable e) {
            errorText = "Ошибка прдключениня к базе данных ";
        }
    }

    public String getJsonPoints() {
        String result = new Gson().toJson(getPoints().stream().map(Points::getCoordinates).toList());
        System.out.println("get json points 1 = " + result);
        return result;
    }

    private void validate(Points point) {
        if (point.getX() > 5 || point.getX() < -3) {
            throw new RuntimeException("x is wrong");
        }

        if (point.getY() > 3 || point.getY() < -5) {
            throw new RuntimeException("y is wrong");
        }

        if (point.getR() > 5 || point.getR() < 1) {
            throw new RuntimeException("r is wrong");
        }
    }

    @Transactional
    public void addJsonPoint() {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            errorText = "";
            double x = Double.parseDouble(params.get("x").replace(',','.'));
            double y = Double.parseDouble(params.get("y").replace(',','.'));
            double r = Double.parseDouble(params.get("r").replace(',','.'));
            System.out.println(x);
            System.out.println(y);
            System.out.println(r);
            Points points = new Points(x, y, r);
            areaCheck.CheckHit(points);
            addPoint(points);
        } catch (Exception e) {
            errorText = "не удалось добавить точку " + e.getMessage();
            e.printStackTrace();
        }
    }
}
