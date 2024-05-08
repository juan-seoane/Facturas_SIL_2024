package modelo.records;

public class Año {
    private int año;
    private int trimestre;

    public Año(int año, int trimestre) {
        this.año = año;
        this.trimestre = trimestre;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }
}
