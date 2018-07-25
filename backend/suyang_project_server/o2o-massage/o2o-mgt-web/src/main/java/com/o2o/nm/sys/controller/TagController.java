package com.o2o.nm.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签Controller
 */
@RequestMapping("tag")
@Controller
public class TagController extends BaseController {

    /**
     * 树结构选择标签（treeselect.tag）
     */
    @RequestMapping(value = "treeselect")
    public String treeselect(Model model, String url, String extId, String checked, String selectIds, String module, String key) {
        model.addAttribute("url", url); // 树结构数据URL
        model.addAttribute("extId", extId); // 排除的编号ID
        model.addAttribute("checked", checked); // 是否可复选
        model.addAttribute("selectIds", selectIds); // 指定默认选中的ID
        model.addAttribute("module", module); // 过滤栏目模型（仅针对CMS的Category树）
        model.addAttribute("key", key);
        return "/modules/sys/tagTreeselect";
    }

    /**
     * 图标选择标签（iconselect.tag）
     */
    @RequestMapping(value = "iconselect")
    public String iconselect(Model model, String value, String key) {
        model.addAttribute("value", value);
        model.addAttribute("key", key);
        return "/modules/sys/tagIconselect";
    }

}
