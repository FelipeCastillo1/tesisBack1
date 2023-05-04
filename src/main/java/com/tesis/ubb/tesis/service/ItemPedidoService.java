package com.tesis.ubb.tesis.service;

import java.util.List;

import com.tesis.ubb.tesis.models.ItemPedido;

public interface ItemPedidoService {
    public ItemPedido findById(Long id);

    public Integer contiene(List<ItemPedido> items,Long idProucto);
    public ItemPedido modificar(ItemPedido item,Long idItem);

    public void delete(ItemPedido item);

    public void deleteGrupo(List<ItemPedido> items);
    
    public Integer cantidadEnRegisto(Long item);
    
}