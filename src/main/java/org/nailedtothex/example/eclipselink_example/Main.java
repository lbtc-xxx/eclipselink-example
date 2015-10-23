package org.nailedtothex.example.eclipselink_example;

import org.nailedtothex.example.eclipselink_entity.Dept;
import org.nailedtothex.example.eclipselink_entity.Employee;
import org.nailedtothex.example.eclipselink_entity.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("myPU");
            EntityManager em = null;

            // Populating data
            try {
                em = emf.createEntityManager();
                final EntityTransaction tx = em.getTransaction();
                tx.begin();

                Dept dept = new Dept();
                dept.setId(1l);
                dept.setDeptName("Engineering");
                dept.setEmployees(new ArrayList<>());
                em.persist(dept);

                Employee emp = new Employee();
                emp.setId(1l);
                emp.setFirstName("Jane");
                emp.setLastName("Doe");
                dept.getEmployees().add(emp);
                emp.setDept(dept);
                em.persist(emp);

                Phone phone = new Phone();
                phone.setPhoneNumber("000-1111-2222");
                phone.setEmployee(emp);
                emp.setPhone(phone);
                em.persist(phone);

                tx.commit();
            } finally { if (em != null) { em.close(); } }

            System.out.println("<<< Populating done >>>");

            try {
                em = emf.createEntityManager();
                final Employee emp = em.find(Employee.class, 1l);

                System.out.println(emp.getFirstName() + " " + emp.getLastName());

                // EAGER
                System.out.println(emp.getDept().getDeptName());
                // LAZY
                System.out.println(emp.getPhone().getPhoneNumber());
            } finally { if (em != null) { em.close(); } }
        } finally { if (emf != null) { emf.close(); } }
    }
}
