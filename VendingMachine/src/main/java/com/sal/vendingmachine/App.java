
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws VendingMachineException{
        // declare variables
     //UserIO io = new UserIOImpl();
     //VendingMachineView view = new VendingMachineView(io);
     //VendingMachineDao dao = new VendingMachineDaoImpl();
     //AuditDao adao = new AuditDaoImpl();

     //VendingMachineService myService = new VendingMachineServiceImpl(dao,adao);
     //VendingMachineController controller = new VendingMachineController(view,myService);

     //controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);

        controller.run();
    }
}
