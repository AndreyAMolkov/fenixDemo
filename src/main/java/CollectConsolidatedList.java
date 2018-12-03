import java.util.ArrayList;

public class CollectConsolidatedList {
//
//
//    public ArrayList<ConsolidatedList> collect(ArrayList <EmployeeOld> staff){
//ConsolidatedList consolidatedList;
//ArrayList<ConsolidatedList>list=new ArrayList <>(0);
//
//        for(EmployeeOld employeeOld :staff){
//            consolidatedList=new ConsolidatedList();
//
//           if(!isProjectManager(employeeOld,consolidatedList))
//               if(!isTeamLeader(employeeOld,consolidatedList))
//                   if(!isEngineer(employeeOld,consolidatedList))
//                       if(!isPersonal(employeeOld,consolidatedList))
//                           getDataFromEmployee(employeeOld,consolidatedList);
//
//            list.add(consolidatedList);
//        }
//
//        return list;
//    }
//
//  }
//
//  private void getDataFromEmployee(EmployeeOld employeeOld, ConsolidatedList consolidated){
//
//        consolidated.setPosition(employeeOld.getPosition());
//        consolidated.setPayment(employeeOld.getPayment());
//        consolidated.setBonus(employeeOld.getBonus());
//        consolidated.setId(employeeOld.getId());
//        consolidated.setName(employeeOld.getName());
//        consolidated.setWorkTime(employeeOld.getWorkTime());
//        consolidated.setProject(employeeOld.getProject());
//    }
//    private void getDataFromPersonal(Personal personal,ConsolidatedList consolidated){
//        getDataFromEmployee((EmployeeOld)personal,consolidated);
//         consolidated.setBase(personal.getBase());
//    }
//    private void getDataFromEngineer(Engineer engineer,ConsolidatedList consolidated){
//        getDataFromEmployee((EmployeeOld)engineer,consolidated);
//        consolidated.setBase(engineer.getBase());
//    }
//    private void getDataFromTeamLeader(TeamLeader teamLeader,ConsolidatedList consolidated){
//       getDataFromEngineer((Engineer)teamLeader,consolidated);
//        consolidated.setSubordinates(teamLeader.getSubordinates());
//        consolidated.setRateFromManagingOfTeam(teamLeader.getRateFromManagingOfTeam());
//        consolidated.setHeadingBonus(teamLeader.getHeadingBonus());
//        consolidated.setNumberSubordinates(teamLeader.getNumberSubordinates());
//    }
//
//    private void getDataFromProjectManager(ProjectManager projectManager,ConsolidatedList consolidated){
//        getDataFromEmployee((EmployeeOld)projectManager,consolidated);
//        consolidated.setSubordinates(projectManager.getSubordinates());
//        consolidated.setRateFromManagingOfTeam(projectManager.getRateFromManagingOfTeam());
//        consolidated.setHeadingBonus(projectManager.getHeadingBonus());
//        consolidated.setNumberSubordinates(projectManager.getNumberSubordinates());
//    }
}
