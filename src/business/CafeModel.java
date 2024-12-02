package business;

import factory.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ownUtil.*;

public class CafeModel implements Observable {
    private Cafe cafe;
    private static CafeModel cafeModel;

    private List<Observer> observers = new LinkedList<Observer>();

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        notifyObservers(); 
    }

    private CafeModel() {
    }

    public static CafeModel getInstance() {
        if (cafeModel == null) {
            cafeModel = new CafeModel();
        }
        return cafeModel;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }

    public void leseAusDatei(String typ) throws IOException {
        Creator creator = new ConcreteCreator();
        Product reader = creator.createCr(typ);

        String[] zeile = reader.leseAusDatei();
        this.cafe = new Cafe(zeile[0], zeile[1], zeile[2], Boolean.parseBoolean(zeile[3]), zeile[4].split(";"));

        reader.schliesseDatei();
        notifyObservers();
    }

    public void schreibeCafeInCsvDatei() throws IOException {
        if (this.cafe != null) {
            try (BufferedWriter aus = new BufferedWriter(new FileWriter("CafeAusgabe.csv", true))) {
                aus.write(cafe.gibCafeZurueck(';'));
                aus.newLine();
            }
        } else {
            throw new IllegalStateException("Kein Cafe gespeichert.");
        }
    }
}

