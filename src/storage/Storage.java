/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import database.connection.ConnectionFactory;
import domain.Linija;
import domain.LinijaMedjustanice;
import domain.LinijaStanica;
import domain.Stanica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anakl
 */
public class Storage {

    List<Stanica> stanice;
    List<LinijaMedjustanice> linijeMedjustanice;
    List<LinijaMedjustanice> linijeMedjustaniceSaFilterom;

    public Storage() {
        stanice = new ArrayList<>();
        linijeMedjustanice = new ArrayList<>();
        linijeMedjustaniceSaFilterom = new ArrayList<>();

    }

    public List<Stanica> getAllStanice() throws Exception {
        try {
            String upit = "SELECT * FROM stanica";

            Statement statement = ConnectionFactory.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(upit);

            while (rs.next()) {
                Stanica s = new Stanica();
                s.setStanicaID(rs.getLong("StanicaID"));
                s.setNazivStanice(rs.getString("NazivStanice"));
                stanice.add(s);
            }
            rs.close();
            statement.close();
            return stanice;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public Linija sacuvajLiniju(Linija linija) throws Exception {
        try {
            String upit = "INSERT INTO linija (NazivLinije, PocetnaStanica, KrajnjaStanica) VALUES(?,?,?)";

            PreparedStatement statement = ConnectionFactory.getInstance().getConnection().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, linija.getNazivLinije());
            statement.setLong(2, linija.getPocetnaStanica());
            statement.setLong(3, linija.getKrajnjaStanica());
            statement.executeUpdate();
            Long id = 0l;
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getLong(1);
                linija.setLinijaID(id);
            }
            ConnectionFactory.getInstance().getConnection().commit();
            rs.close();
            statement.close();
            return linija;
        } catch (Exception ex) {
            ConnectionFactory.getInstance().getConnection().rollback();
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public void sacuvajMedjustanice(List<LinijaStanica> linijeStanice) throws Exception {
        try {
            String upit = "INSERT INTO linijastanica(LinijaID, StanicaID) VALUES (?,?)";

            PreparedStatement statement = ConnectionFactory.getInstance().getConnection().prepareStatement(upit);
            for (LinijaStanica linijaStanica : linijeStanice) {
                statement.setLong(1, linijaStanica.getLinija().getLinijaID());
                statement.setLong(2, linijaStanica.getStanica().getStanicaID());
                statement.executeUpdate();
            }
            ConnectionFactory.getInstance().getConnection().commit();
            statement.close();

        } catch (Exception ex) {
            ConnectionFactory.getInstance().getConnection().rollback();
            ex.printStackTrace();
            throw new Exception();
        }

    }
//SELECT l.`NazivLinije`, st.`NazivStanice`, sta.`NazivStanice`, s.`NazivStanice` FROM linija l JOIN linijastanica ls ON (l.`LinijaID`=ls.`LinijaID`) JOIN stanica s ON (ls.`StanicaID` = s.`StanicaID`) JOIN stanica st ON(l.`PocetnaStanica` = st.`StanicaID`) JOIN stanica sta ON (l.`KrajnjaStanica` = sta.`StanicaID`) WHERE l.`LinijaID`=6;

    public List<LinijaMedjustanice> getAllLinijeMedjustanice() throws Exception {
        linijeMedjustanice.clear();
        try {
            String upit = "SELECT l.NazivLinije AS 'NazivLinije', st.NazivStanice AS 'PocetnaStanica', stat.NazivStanice AS 'KrajnjaStanica', s.NazivStanice AS 'Medjustanica' FROM linija l JOIN linijastanica ls ON (l.LinijaID=ls.LinijaID) JOIN stanica s ON (ls.StanicaID=s.StanicaID) JOIN stanica st ON (l.PocetnaStanica=st.StanicaID) JOIN stanica stat ON (l.KrajnjaStanica=stat.StanicaID)";

            Statement statement = ConnectionFactory.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                LinijaMedjustanice lm = new LinijaMedjustanice();
                if (daLiPostojiLinijaMedjustanice(rs.getString("NazivLinije")) == -1) {
                    lm.setNazivLinije(rs.getString("NazivLinije"));
                    lm.setPocetneStanica(rs.getString("PocetnaStanica"));
                    lm.setKrajnjaStanica(rs.getString("KrajnjaStanica"));
                    lm.setMedjustanica(rs.getString("Medjustanica"));
                    linijeMedjustanice.add(lm);
                } else {
                    lm = vratiLinijuMedjustanice(rs.getString("NazivLinije"));
                    String staraM = lm.getMedjustanica();
                    lm.setMedjustanica(rs.getString("Medjustanica"));
                    String novaM = lm.getMedjustanica();
                    String spojenaM = staraM + ", " + novaM;
                    lm.setMedjustanica(spojenaM);
                }
            }
            rs.close();
            statement.close();
            return linijeMedjustanice;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }

    }

    public int daLiPostojiLinijaMedjustanice(String nazivLinije) {
        for (LinijaMedjustanice lm : linijeMedjustanice) {
            if (lm.getNazivLinije().equalsIgnoreCase(nazivLinije)) {
                return 1; //ako postoji u listi data linija
            }
        }
        return -1;
    }

    public LinijaMedjustanice vratiLinijuMedjustanice(String naziv) {
        for (LinijaMedjustanice linijaM : linijeMedjustanice) {
            if (linijaM.getNazivLinije().equalsIgnoreCase(naziv)) {
                return linijaM;
            }
        }
        return null;
    }

    public List<LinijaMedjustanice> getAllLinijeMedjustaniceSaFilterom(String naziv) throws Exception {
        linijeMedjustaniceSaFilterom.clear();
        try {
            String upit = "SELECT l.NazivLinije, st.NazivStanice AS 'PocetnaStanica', stat.NazivStanice AS 'KrajnjaStanica', s.NazivStanice AS 'Medjustanica' FROM linija l JOIN linijastanica ls ON (l.LinijaID=ls.LinijaID) JOIN stanica s ON (ls.StanicaID=s.StanicaID) JOIN stanica st ON (l.PocetnaStanica=st.StanicaID) JOIN stanica stat ON (l.KrajnjaStanica=stat.StanicaID) WHERE st.NazivStanice LIKE '%" + naziv + "%' OR stat.NazivStanice LIKE '%" + naziv + "%' OR s.NazivStanice LIKE '%" + naziv + "%'";

            Statement statement = ConnectionFactory.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                LinijaMedjustanice lm = new LinijaMedjustanice();
                if (daLiPostojiLinijaMedjustanice(rs.getString("NazivLinije")) == -1) {
                    lm.setNazivLinije(rs.getString("NazivLinije"));
                    lm.setPocetneStanica(rs.getString("PocetnaStanica"));
                    lm.setKrajnjaStanica(rs.getString("KrajnjaStanica"));
                    lm.setMedjustanica(rs.getString("Medjustanica"));
                    linijeMedjustaniceSaFilterom.add(lm);
                } else {
                    lm = vratiLinijuMedjustanice(rs.getString("NazivLinije"));
                    String staraM = lm.getMedjustanica();
                    lm.setMedjustanica(rs.getString("Medjustanica"));
                    String novaM = lm.getMedjustanica();
                    String spojenaM = staraM + ", " + novaM;
                    lm.setMedjustanica(spojenaM);
                }
            }
            rs.close();
            statement.close();
            return linijeMedjustaniceSaFilterom;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }
}
