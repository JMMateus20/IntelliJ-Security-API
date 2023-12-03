package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroProductoDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseProductoDTO;
import com.cursos.api.springsecuritycourse.entity.Categoria;
import com.cursos.api.springsecuritycourse.entity.Producto;
import com.cursos.api.springsecuritycourse.entity.Status;
import com.cursos.api.springsecuritycourse.exception.NotFoundExceptionManaged;
import com.cursos.api.springsecuritycourse.repository.CategoriaRepository;
import com.cursos.api.springsecuritycourse.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRep;

    @Autowired
    private CategoriaRepository categoriaRep;
    @Override
    public Page<ResponseProductoDTO> findAll(Pageable pageable) {
        Page<Producto> productos=productoRep.encontrarTodos(Status.ENABLED, Status.ENABLED, pageable);

        List<ResponseProductoDTO> response=productos.getContent().stream().map(prod->new ResponseProductoDTO(prod.getId(), prod.getNombre(), prod.getPrecio(), prod.getCategoria().getNombre())).collect(Collectors.toList());

        return new PageImpl<>(response, pageable, productos.getTotalElements());


    }

    @Override
    public ResponseEntity<?> buscar(Long id) {
        Producto productoBD=productoRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Producto no encontrado"));
        ResponseProductoDTO response=new ResponseProductoDTO(productoBD.getId(), productoBD.getNombre(), productoBD.getPrecio(), productoBD.getCategoria().getNombre());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> insertar(RegistroProductoDTO datos) {

        Categoria categoriaBD=categoriaRep.findById(datos.getCategoriaID()).orElseThrow(()->new NotFoundExceptionManaged("categoria no encontrada"));

        Producto productoNew=new Producto(datos.getNombre(), datos.getPrecio(), Status.ENABLED, categoriaBD);
        productoRep.save(productoNew);

        ResponseProductoDTO response=new ResponseProductoDTO(productoNew.getId(), productoNew.getNombre(), productoNew.getPrecio(), productoNew.getCategoria().getNombre());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> cambiarEstado(Long id, Status status) {
        Producto productoBD=productoRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Producto no encontrado"));
        productoBD.setStatus(status);
        productoRep.save(productoBD);
        return ResponseEntity.ok("Estado cambiado con éxito");

    }

    @Override
    public ResponseEntity<?> actualizar(Long id, RegistroProductoDTO datos) {
        Producto productoBD=productoRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Producto no encontrado"));
        Categoria categoriaBD=categoriaRep.findById(datos.getCategoriaID()).orElseThrow(()->new NotFoundExceptionManaged("Categoria no encontrada"));
        productoBD.setNombre(datos.getNombre());
        productoBD.setPrecio(datos.getPrecio());
        productoBD.setCategoria(categoriaBD);
        productoRep.save(productoBD);
        ResponseProductoDTO response=new ResponseProductoDTO(productoBD.getId(), productoBD.getNombre(), productoBD.getPrecio(), productoBD.getCategoria().getNombre());
        return ResponseEntity.ok(response);
    }
}
