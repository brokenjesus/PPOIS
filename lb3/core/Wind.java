package by.lupach.weatherapp.core;

public class Wind {
    private int degree;
    private Speed speed;
    private float gust;

    Wind(int degree, float speed, float gust){
        this.degree = degree;
        this.speed = new Speed(speed);
        this.gust = gust;
    }

    public int getDegree() {
        return degree;
    }
    public Speed getSpeed() {
        return speed;
    }
    public float getGust() {
        return gust;
    }
}
