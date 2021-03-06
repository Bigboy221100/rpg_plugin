package main.Chars;

import main.MySQL.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by user on 27.09.2017.
 */
public class Charvoids {
    public Charvoids() {
    }

    public static boolean isUsed(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters");
        try {
            while (rs.next()) {
                if (rs.getString("charname").equalsIgnoreCase(charname)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean charbelongsto(String p) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters");
        try {
            while (rs.next()) {
                if (rs.getString("UUID").equalsIgnoreCase(p)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getcharClass(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return rs.getString("charclass");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getcharname(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return rs.getString("charname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getcharmoney(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return Integer.parseInt(rs.getString("charmoney"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getcharlevel(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return Integer.parseInt(rs.getString("charlevel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setcharlevel(int level, String charname) {
        MySQL.update("UPDATE Characters SET charlevel='" + level + "' WHERE charname='" + charname + "'");
    }

    public static double getcharxp(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return Double.parseDouble(rs.getString("charxp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setcharxp(float xp, String charname) {
        MySQL.update("UPDATE Characters SET charxp=" + xp + " WHERE charname='" + charname + "'");
    }

    public static String getcharInv(String charname) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE charname='" + charname + "'");
        try {
            while (rs.next()) {
                return rs.getString("charinv");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setcharinv(String inv, String charname) {
        MySQL.update("UPDATE Characters SET charinv='" + inv + "' WHERE charname='" + charname + "'");
    }

    public static String getcurrentchar(String UUID) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE UUID='" + UUID + "' AND currentplaying=1");
        try {
            while (rs.next()) {
                return rs.getString("charname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void logoutcurrentchar(String UUID) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE UUID='" + UUID + "' AND currentplaying=1");
        try {
            while (rs.next()) {
                MySQL.update("UPDATE Characters SET Characters.currentplaying=0;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object[] getuserchars(String UUID) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM Characters WHERE UUID='" + UUID + "'");
        ArrayList<String> help = new ArrayList<>();
        try {
            while (rs.next()) {
                help.add(rs.getString("charname"));
            }
            Object[] erg = help.toArray();
            return erg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
