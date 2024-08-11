package com.hust.mymall.controller;

import com.hust.mymall.consts.MallConst;
import com.hust.mymall.form.ShoppingForm;
import com.hust.mymall.pojo.User;
import com.hust.mymall.service.IShoppingService;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@RestController
public class ShoppingController {
    @Autowired
    private IShoppingService shoppingService;

    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShoppingForm shoppingForm, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shoppingService.add(user.getId(), shoppingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shoppingService.delete(user.getId(), shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShoppingForm shoppingForm,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shoppingService.update(user.getId(), shippingId, shoppingForm);
    }

    @GetMapping("shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                           HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shoppingService.list(user.getId(), pageNum, pageSize);
    }

}
