package es.rentcarplus.dao;

import es.rentcarplus.model.Cliente;

public interface ClienteDAO {

    Long insertarCliente(Cliente cliente) throws Exception;

    
}