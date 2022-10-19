package com.example.smarteping.modelo;

public class registrokilosdiarios {

    public String getFechaDia() {
        return fechaDia;
    }

    public void setFechaDia(String fechaDia) {
        this.fechaDia = fechaDia;
    }

    public Double getKilosproducidos() {
        return kilosproducidos;
    }

    public void setKilosproducidos(Double kilosproducidos) {
        this.kilosproducidos = kilosproducidos;
    }

    public registrokilosdiarios(String fechaDia, Double kilosproducidos) {
        this.fechaDia = fechaDia;
        this.kilosproducidos = kilosproducidos;
    }

    public registrokilosdiarios(){
    }

    private String fechaDia;
    private Double kilosproducidos;
}
