package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Line;

import java.util.ArrayList;

public interface LineDAO {

    ArrayList<Line> getLines();

    Line getLine(int id);

    boolean addLine(Line Line);

    int deleteLine(int id);

    int deleteAllLine();
}
