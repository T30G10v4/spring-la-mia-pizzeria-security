package me.matteogiovagnotti.springlamiapizzeria.exceptions;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String message){

        super(message);

    }

}

