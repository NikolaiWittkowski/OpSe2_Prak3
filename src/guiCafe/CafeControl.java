package guiCafe;

import business.Cafe;
import ownUtil.*;
import business.CafeModel;
import javafx.stage.Stage;

public class CafeControl implements Observer {
    private CafeView caView;
    private CafeModel caMod;

    public CafeControl(Stage primaryStage) {
        this.caMod = CafeModel.getInstance();
        this.caView = new CafeView(this, primaryStage, caMod);
        this.caMod.addObserver(this);
    }

    public void nehmeCafeAuf() {
        try {
            Cafe cafe = new Cafe(
                caView.getTxtName(),
                caView.getTxtOrt(),
                caView.getTxtBeschreibung(),
                Boolean.parseBoolean(caView.getTxtMitBackwerk()),
                caView.getTxtKaffeeprodukte().split(";")
            );
            caMod.setCafe(cafe); 
            caView.zeigeCafeAn(); 
        } catch (Exception e) {
            caView.zeigeFehlermeldungsfensterAn("Fehler: " + e.getMessage());
        }
    }

    public void zeigeCafeAn() {
        Cafe cafe = caMod.getCafe();
        if (cafe != null) {
            caView.setTxtAnzeige(cafe.gibCafeZurueck(';'));
        } else {
            caView.setTxtAnzeige("Kein Cafe verfügbar.");
        }
    }

    public void importiereCafe(String typ) {
        try {
            caMod.leseAusDatei(typ);
            caView.zeigeCafeAn(); 
            caView.zeigeInformationsfensterAn("Daten erfolgreich importiert.");
        } catch (Exception e) {
            caView.zeigeFehlermeldungsfensterAn("Fehler: " + e.getMessage());
        }
    }

    public void exportiereCafe() {
        try {
            caMod.schreibeCafeInCsvDatei();
            caView.zeigeInformationsfensterAn("Daten erfolgreich exportiert.");
        } catch (Exception e) {
            caView.zeigeFehlermeldungsfensterAn("Fehler: " + e.getMessage());
        }
    }

    @Override
    public void update() {
       
        this.caView.zeigeCafeAn();
    }
}

