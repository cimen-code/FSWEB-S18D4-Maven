package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/burger", "/workintech/burgers"})
public class BurgerController {

    private final BurgerDao burgerDao;

    @GetMapping
    public List<Burger> findAll() {
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger findById(@PathVariable Long id) {
        return burgerDao.findById(id);
    }

    @PostMapping
    public Burger save(@RequestBody Burger burger) {
        BurgerValidation.validateNew(burger);
        return burgerDao.save(burger);
    }

    @PutMapping
    public Burger update(@RequestBody Burger burger) {
        BurgerValidation.validateUpdate(burger);
        return burgerDao.update(burger);
    }

    @PutMapping("/{id}")
    public Burger updateById(
            @PathVariable Long id,
            @RequestBody Burger burger) {

        burger.setId(id);
        return update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger remove(@PathVariable Long id) {
        return burgerDao.remove(id);
    }

    @GetMapping("/price/{price}")
    public List<Burger> findByPrice(@PathVariable int price) {
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/breadType/{breadType}")
    public List<Burger> findByBreadType(
            @PathVariable BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/content/{content}")
    public List<Burger> findByContent(
            @PathVariable String content) {
        return burgerDao.findByContent(content);
    }

    @GetMapping("/findByPrice")
    public List<Burger> findByPriceFromBody(
            @RequestBody Map<String, Integer> body) {
        Integer price = body.get("price");
        if (price == null) {
            throw new BurgerException(
                "Price is required", HttpStatus.BAD_REQUEST
            );
        }
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/findByBreadType")
    public List<Burger> findByBreadTypeFromBody(
            @RequestBody Map<String, String> body) {
        String value = body.get("breadType");
        if (value == null) {
            throw new BurgerException(
                "Bread type is required", HttpStatus.BAD_REQUEST
            );
        }
        try {
            return burgerDao.findByBreadType(
                BreadType.valueOf(value.toUpperCase())
            );
        } catch (IllegalArgumentException ex) {
            throw new BurgerException(
                "Invalid bread type", HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/findByContent")
    public List<Burger> findByContentFromBody(
            @RequestBody Map<String, String> body) {
        String content = body.get("content");
        if (content == null) {
            throw new BurgerException(
                "Content is required", HttpStatus.BAD_REQUEST
            );
        }
        return burgerDao.findByContent(content);
    }
}
