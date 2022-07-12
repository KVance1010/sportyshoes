package com.sportyshoes.controllers.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sportyshoes.exceptions.UserNotFoundException;
import com.sportyshoes.model.User;
import com.sportyshoes.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String listAllUsers(Model model) {
		model.addAttribute("listUsers", userService.listAllUsers());
		model.addAttribute("pageTitle", "User List");
		return "users/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("listRoles", userService.listAllRoles());
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Create New User");
		return "users/users_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		userService.saveUser(user);
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUserById(id);
			model.addAttribute("user", user);
			model.addAttribute("listRoles", userService.listAllRoles());
			model.addAttribute("pageTitle", "Edit Users (ID: " + id + ")");
			return "users/users_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			userService.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "The user with ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		userService.updateUserEnableStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user with ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}
	
}
