package com.sportyshoes.controllers.frontend;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sportyshoes.model.Role;
import com.sportyshoes.model.User;
import com.sportyshoes.service.FileUploadUtil;
import com.sportyshoes.service.UserService;

@Controller
public class registration {
	
	@Autowired
	private UserService service;

	
	@GetMapping("/registration")
	public String newCustomerUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "New User");
		return "/customers/registration";
	}
	
	@PostMapping("/registration/save")
	public String saveCustomerUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		Role role = service.findRoleById(3);
		user.addRole(role);
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);

			User savedUser = service.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			if (user.getPhotos().isEmpty())
				user.setPhotos(null);
			service.save(user);
		}

		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

		return "redirect:/login";
	}


}
