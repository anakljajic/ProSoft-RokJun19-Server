/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.LinijaMedjustanice;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anakl
 */
public class TableModelServer extends AbstractTableModel {

    private List<LinijaMedjustanice> linijeMedjustanice;
    String[] columnNames = new String[]{"Naziv linije", "Pocetna stanica", "Krajnja stanica", "Medjustanice"};

    public TableModelServer(List<LinijaMedjustanice> linijeMedjustanice) {
        this.linijeMedjustanice = linijeMedjustanice;
    }

    @Override
    public int getRowCount() {
        return linijeMedjustanice.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LinijaMedjustanice lm = linijeMedjustanice.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return lm.getNazivLinije();
            case 1:
                return lm.getPocetneStanica();
            case 2:
                return lm.getKrajnjaStanica();
            case 3:
                return lm.getMedjustanica();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void azuriraj(List<LinijaMedjustanice> linijeMedjustanice) {
        this.linijeMedjustanice = linijeMedjustanice;
        fireTableDataChanged();
    }

}
