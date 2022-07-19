package controller;

import dao.CategoryDao;
import dao.ProductDao;
import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.CrudCategory;
import service.CrudProduct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CrudCategory crudCategory;

    @Autowired
    CrudProduct crudProduct;

    @Autowired
    ProductDao productDao ;

    @Autowired
    CategoryDao categoryDao;

    @GetMapping("/home")
    public ModelAndView show(){
        ModelAndView modelAndView = new ModelAndView("Home");
        modelAndView.addObject("products", crudProduct.getAll());
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("categories",crudCategory.getAll());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Product product , @RequestParam MultipartFile file, @RequestParam int categoryId){

        String nameImg = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File("D:\\00. Codegym\\04. Module 4\\5. CSDL and ORM\\ORM\\src\\main\\webapp\\WEB-INF\\img\\" + nameImg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String img = "/img/" + nameImg;


        product.setImg(img);
        product.setCategory(crudCategory.findById(categoryId));
        crudProduct.save(product);
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        return modelAndView;
    }


    @GetMapping("/edit")
    public ModelAndView showEdit(@RequestParam int id, Model model) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product p = productDao.findById(id);
        modelAndView.addObject("product", p);
        modelAndView.addObject("categories",crudCategory.getAll());

        Boolean[] statusT = {true,false};
        modelAndView.addObject("statusT",statusT);

        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product , @RequestParam MultipartFile file, @RequestParam int categoryId) {
        String nameImg = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File("D:\\00. Codegym\\04. Module 4\\5. CSDL and ORM\\ORM\\src\\main\\webapp\\WEB-INF\\img\\" + nameImg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String img = "/img/" + nameImg;


        product.setImg(img);
        product.setCategory(crudCategory.findById(categoryId));


        crudProduct.edit(product);
        return "redirect:/home";
    }

    @PostMapping("/Search")
    public String search(@RequestParam String search,Model model) {
        List<Product> findSearch = new ArrayList<>();
        for (Product p : crudProduct.getAll()) {
            if (p.getName().contains(search)) {
                findSearch.add(p);
            }
        }
        model.addAttribute("products", findSearch);
        return "Home";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
       crudProduct.delete(id);
        return "redirect:/home";
    }


//    --------------------thao tác với Category------------------------------

    @GetMapping("/homeCat")
    public ModelAndView showCat(){
        ModelAndView modelAndView = new ModelAndView("HomeCat");
        modelAndView.addObject("categories", crudCategory.getAll());
        return modelAndView;
    }
    @GetMapping("/createCat")
    public ModelAndView showCreateCat(){
        ModelAndView modelAndView = new ModelAndView("createCat");
        return modelAndView;
    }
    @PostMapping("/createCat")
    public ModelAndView createCat(@ModelAttribute Category category) {

        crudCategory.save(category);
        ModelAndView modelAndView = new ModelAndView("redirect:/homeCat");
        return modelAndView;
    }


    @GetMapping("/editCat")
    public ModelAndView showEditCat(@RequestParam int id, Model model) {
        ModelAndView modelAndView = new ModelAndView("editCat");
        Category p = categoryDao.findById(id);
        modelAndView.addObject("category", p);
        return modelAndView;
    }

    @PostMapping("/editCat")
    public String editCat(@ModelAttribute Category category) {

        crudCategory.edit(category);
        return "redirect:/homeCat";
    }

    @PostMapping("/SearchCat")
    public String searchCat(@RequestParam String search,Model model) {
        List<Category> findSearch = new ArrayList<>();
        for (Category p : crudCategory.getAll()) {
            if (p.getName().contains(search)) {
                findSearch.add(p);
            }
        }
        model.addAttribute("categories", findSearch);
        return "HomeCat";
    }

    @GetMapping("/deleteCat")
    public String deleteCat(@RequestParam int id) {
        crudProduct.delete(id);
        return "redirect:/homeCat";
    }
}
