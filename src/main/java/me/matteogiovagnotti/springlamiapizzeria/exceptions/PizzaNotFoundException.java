package me.matteogiovagnotti.springlamiapizzeria.exceptions;

public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(String message){

        super(message);

    }

}
