package com.schin.notas.utils;

/**
 * Created by schin on 20/04/2017.
 */

public enum Chave{
    NOME{
        @Override
        public String toString() {
            return "NOME";
        }
    }, SOBRENOME{
        @Override
        public String toString() {
            return "SOBRENOME";
        }
    }, DATA_NASCIMENTO{
        @Override
        public String toString() {
            return "DATA_NASCIMENTO";
        }
    }, EMAIL{
        @Override
        public String toString() {
            return "EMAIL";
        }
    }, SENHA{
        @Override
        public String toString() {
            return "SENHA";
        }
    }, SEXO{
        @Override
        public String toString() {
            return "SEXO";
        }
    }
}
