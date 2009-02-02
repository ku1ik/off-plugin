/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kill
 */
public class OffListModelTest {

    public OffListModelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setProjectFilesProvider method, of class OffListModel.
     */
    @Test
    public void testSetProjectFilesProvider() {
        System.out.println("setProjectFilesProvider");
        ProjectProvider pfp = null;
        OffListModel instance = null;
        instance.setProjectFilesProvider(pfp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFilter method, of class OffListModel.
     */
    @Test
    public void testSetFilter() {
        System.out.println("setFilter");
        String f = "";
        OffListModel instance = null;
        instance.setFilter(f);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refresh method, of class OffListModel.
     */
    @Test
    public void testRefresh() {
        System.out.println("refresh");
        OffListModel instance = null;
        instance.refresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getElementAt method, of class OffListModel.
     */
    @Test
    public void testGetElementAt() {
        System.out.println("getElementAt");
        int index = 0;
        OffListModel instance = null;
        Object expResult = null;
        Object result = instance.getElementAt(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class OffListModel.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        OffListModel instance = null;
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementAccessCounter method, of class OffListModel.
     */
    @Test
    public void testIncrementAccessCounter() {
        System.out.println("incrementAccessCounter");
        ProjectFile pf = null;
        OffListModel instance = null;
        instance.incrementAccessCounter(pf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}