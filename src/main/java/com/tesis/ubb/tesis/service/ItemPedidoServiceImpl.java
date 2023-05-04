package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tesis.ubb.tesis.models.ItemPedido;
import com.tesis.ubb.tesis.repository.ItemPedidoRepository;


@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{

    @Autowired
	private ItemPedidoRepository itemPedidoRepository;


    @Override
    public ItemPedido findById(Long id){
        return itemPedidoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Integer contiene(List<ItemPedido> items,Long idProucto){
        Integer cantidad=0;
        for (ItemPedido item : items) {
            if(item.getProducto().getId()==idProucto){
                cantidad=item.getCantidad();
                return cantidad;
            }
        }
        return cantidad;
    }

    @Override
    @Transactional
    public ItemPedido modificar(ItemPedido item,Long idItem){
        ItemPedido itemActual=this.findById(idItem);
        itemActual.setCantidad(item.getCantidad());
        itemPedidoRepository.save(itemActual);
        return itemActual;
    }

    @Override
    @Transactional
    public void delete(ItemPedido item){
        itemPedidoRepository.delete(item);
    }

    @Override
    @Transactional
    public void deleteGrupo(List<ItemPedido> items){
        for (ItemPedido itemPedido : items) {
            this.delete(itemPedido);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer cantidadEnRegisto(Long item){
        Integer valor;
        ItemPedido iP=this.findById(item);
        valor=iP.getCantidad();
        return valor;
    }
}