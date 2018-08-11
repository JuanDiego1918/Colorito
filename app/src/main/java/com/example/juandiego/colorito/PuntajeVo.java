package com.example.juandiego.colorito;

public class PuntajeVo {

    private String desplegas;
    private String correctas;
    private String incorrectas;
    private String fallidos;
    private int timepoPalabra;
    private int numeroIntentos;
    private int tiempoTotal;
    private int tipo;

    public PuntajeVo(String desplegas, String correctas, String incorrectas, String fallidos) {
        this.desplegas = desplegas;
        this.correctas = correctas;
        this.incorrectas = incorrectas;
        this.fallidos = fallidos;
    }

    public PuntajeVo() {
    }

    public String getDesplegas() {
        return desplegas;
    }

    public void setDesplegas(String desplegas) {
        this.desplegas = desplegas;
    }

    public String getCorrectas() {
        return correctas;
    }

    public void setCorrectas(String correctas) {
        this.correctas = correctas;
    }

    public String getIncorrectas() {
        return incorrectas;
    }

    public void setIncorrectas(String incorrectas) {
        this.incorrectas = incorrectas;
    }

    public String getFallidos() {
        return fallidos;
    }

    public void setFallidos(String fallidos) {
        this.fallidos = fallidos;
    }

    public int getTimepoPalabra() {
        return timepoPalabra;
    }

    public void setTimepoPalabra(int timepoPalabra) {
        this.timepoPalabra = timepoPalabra;
    }

    public int getNumeroIntentos() {
        return numeroIntentos;
    }

    public void setNumeroIntentos(int numeroIntentos) {
        this.numeroIntentos = numeroIntentos;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(int tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
