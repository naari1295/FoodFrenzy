package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;

@Component
public class AdminServices
{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Admin> getAll()
    {
        List<Admin> admins =
            (List<Admin>) this.adminRepository.findAll();

        return admins;
    }


    public Admin getAdmin(int id)
    {
        Optional<Admin> optional =
            this.adminRepository.findById(id);

        Admin admin = optional.get();

        return admin;
    }


    // UPDATE ADMIN
    public void update(Admin admin, int id)
    {
        for (Admin ad : getAll())
        {
            if(ad.getAdminId() == id)
            {
                /*
                 * Keep the same admin ID.
                 * Remove this line if your Admin entity
                 * does not have setAdminId().
                 */
                admin.setAdminId(id);

                // Hash password before saving
                admin.setAdminPassword(
                    passwordEncoder.encode(
                        admin.getAdminPassword()
                    )
                );

                this.adminRepository.save(admin);
            }
        }
    }


    // DELETE ADMIN
    public void delete(int id)
    {
        this.adminRepository.deleteById(id);
    }


    // ADD ADMIN
    public void addAdmin(Admin admin)
    {
        // Hash password before saving
        admin.setAdminPassword(
            passwordEncoder.encode(
                admin.getAdminPassword()
            )
        );

        this.adminRepository.save(admin);
    }


    // ADMIN LOGIN
    public boolean validateAdminCredentials(
        String email,
        String password)
    {
        Admin admin =
            adminRepository.findByAdminEmail(email);

        if(admin != null &&
           passwordEncoder.matches(
               password,
               admin.getAdminPassword()))
        {
            return true;
        }

        return false;
    }
}