package ghc;

import ghc.njdg.DashBoard;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	DashBoard dashboard = new DashBoard();
    	dashboard.setLastMonthFiledCases(10, 10, 5);
    	dashboard.setUnderObjectionCases(20, 20, 10);
    	dashboard.setUnderRejectionCases(5, 5, 2);
    	dashboard.setPendingForReg(11, 11, 4);
    	dashboard.setTodayListedCases(22, 22, 20);
    	dashboard.setTotalJudges(30);
        dashboard.writeXML();        
    }
}


