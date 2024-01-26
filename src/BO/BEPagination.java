/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

/**
 *
 * @author allan
 */
public class BEPagination {
   private int currentPage = 1;
   private final int pageSize = 50;
   private int offset = 0;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }
}
