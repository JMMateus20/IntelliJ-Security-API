package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroCategoriaDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    private CategoriaRepository categoriaRep;

    @Autowired
    private ProductoRepository productoRep;


    @Override
    public ResponseEntity<?> insertar(RegistroCategoriaDTO datos) {
        Map<String, Object> respuesta=new HashMap<>();
        if (categoriaRep.findByNombre(datos.getNombre()).isPresent()){
            respuesta.put("mensaje", "Ya se encuentra esta categoría");
            return ResponseEntity.badRequest().body(respuesta);
        }
        Categoria categoriaNew=new Categoria(datos.getNombre(), Status.ENABLED);
        return new ResponseEntity<>(categoriaRep.save(categoriaNew), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Page<Categoria>> listar(Pageable pageable) {
        Page<Categoria> categorias=categoriaRep.findByStatus(Status.ENABLED, pageable);
        return ResponseEntity.ok(categorias);
    }

    @Override
    public ResponseEntity<?> modificar(Long id, RegistroCategoriaDTO datos) {
        Map<String, Object> respuesta=new HashMap<>();
        Categoria categoriaBD=categoriaRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Categoria no encontrada"));
        if (categoriaRep.findByNombre(datos.getNombre()).isPresent()){
            respuesta.put("mensaje", "Una categoria con este nombre ya existe en la base de datos");
            return ResponseEntity.badRequest().body(respuesta);
        }
        categoriaBD.setNombre(datos.getNombre());
        respuesta.put("categoria", categoriaRep.save(categoriaBD));
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<String> cambiarEstado(Long id, Status status) {
        Categoria categoriaBD=categoriaRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Categoria no encontrada"));
        categoriaBD.setStatus(status);
        categoriaRep.save(categoriaBD);
        return ResponseEntity.ok("Estado cambiado con éxito");
    }

    @Override
    public ResponseEntity<?> buscar(Long id) {
        Categoria categoriaBD=categoriaRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Categoria no encontrada"));
        return ResponseEntity.ok(categoriaBD);
    }

    @Override
    public ResponseEntity<Page<ResponseProductoDTO>> listarProductos(Long id, Pageable pageable) {
        Categoria categoriaBD=categoriaRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Categoria no encontrada"));
        Page<Producto> productosPorCategoria=productoRep.findByCategoria(categoriaBD, pageable);
        List<ResponseProductoDTO> productos=productosPorCategoria.getContent().stream()
                .map(prod -> new ResponseProductoDTO(prod.getId(), prod.getNombre(), prod.getPrecio(), prod.getCategoria().getNombre())
                ).collect(Collectors.toList());
        Page<ResponseProductoDTO> respuesta=new PageImpl<>(productos, pageable, productosPorCategoria.getTotalElements());
        if (!respuesta.hasContent()){return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(respuesta);
    }


    @Override
    public ResponseEntity<?> listarProductos2(Long id) {
        Categoria categoriaBD=categoriaRep.findById(id).orElseThrow(()->new NotFoundExceptionManaged("Categoría no encontrada"));
        List<ResponseProductoDTO> productos=categoriaBD.getProductos().stream().map(prod->new ResponseProductoDTO(prod.getId(), prod.getNombre(), prod.getPrecio(), prod.getCategoria().getNombre())).collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }


}
