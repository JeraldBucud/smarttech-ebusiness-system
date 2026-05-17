/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.coit20259.ebusiness.business.exception;

import jakarta.ejb.ApplicationException;

/**
 *
 * @author Cardoso Pepe
 */


@ApplicationException(rollback = true)
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}