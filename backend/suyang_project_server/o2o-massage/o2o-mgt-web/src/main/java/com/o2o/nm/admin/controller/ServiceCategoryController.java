package com.o2o.nm.admin.controller;


import com.o2o.nm.admin.service.ServiceCategoryService;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.sys.controller.BaseController;
import com.o2o.nm.sys.util.ResConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 服务分类表 前端控制器
 * </p>
 *
 * @author warning5
 * @since 2018-03-23
 */
@Controller
@RequestMapping("/serviceCategory")
@ResConfig(name = "服务分类管理", id = "serviceCategory")
public class ServiceCategoryController extends BaseController {

    @Autowired
    ServiceCategoryService serviceCategoryService;

    @RequestMapping("/")
    @ResConfig(name = "显示服务分类列表", id = "serviceCategory1")
    public String list() {
        return "modules/admin/serviceCatList";
    }

    @RequestMapping("all_data")
    @ResConfig(name = "获取全部服务分类", id = "serviceCategory2")
    @ResponseBody
    public TableResponse data() {
        TableResponse response = new TableResponse();
        response.setData(serviceCategoryService.getCats());
        return response;
    }

    @RequestMapping("showEdit")
    @ResConfig(name = "显示服务分类编辑页面", id = "serviceCategory3")
    public String showEdit(Model model, @RequestParam String catId) {
        model.addAttribute("cat", serviceCategoryService.selectById(catId));
        return "modules/admin/serviceCatForm";
    }

    @RequestMapping("save")
    @ResConfig(name = "保存服务", id = "serviceCategory4")
    @ResponseBody
    public String save(ServiceCategory serviceCategory) {
        try {
            serviceCategoryService.insertOrUpdate(serviceCategory);
            return getSuccessJson("保存成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorJson("保存失败");
        }
    }

    @RequestMapping("showAdd")
    @ResConfig(name = "显示服务分类添加页面", id = "serviceCategory5")
    public String showAdd() {
        return "modules/admin/serviceCatForm";
    }

    @RequestMapping("delete")
    @ResConfig(name = "删除服务分类", id = "serviceCategory6")
    @ResponseBody
    public String delete(@RequestParam String catId) {
        try {
            serviceCategoryService.delete(catId);
            return getSuccessJson("删除成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorJson("删除失败 " + e.getMessage());
        }
    }
}

