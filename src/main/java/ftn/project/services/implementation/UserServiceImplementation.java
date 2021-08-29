package ftn.project.services.implementation;

import ftn.project.models.Buyer;
import ftn.project.models.Seller;
import ftn.project.models.User;
import ftn.project.repositories.UserRepository;
import ftn.project.services.BuyerService;
import ftn.project.services.SellerService;
import ftn.project.services.UserService;
import ftn.project.web.dto.ChangePasswordDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User findOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NoSuchElementException("User with id = " + id + " not found!");
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(findOne(id));
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) throws Exception {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if(authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, changePasswordDto.getCurrentPassword()));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");
            return;
        }

        LOGGER.debug("Changing password for user '" + username + "'");

        Seller seller = null;
        Buyer buyer = null;

        try {
            seller = sellerService.findSellerByUsername(username);
            buyer = buyerService.findBuyerByUsername(username);
        } catch (NoSuchElementException ex) {

        }

        if(seller != null && changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatedNewPassword())) {
            seller.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            sellerService.update(seller);
        } else if(buyer != null && changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatedNewPassword())) {
            buyer.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            buyerService.update(buyer);
        } else {
            System.out.println("Change " +changePasswordDto);
            if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatedNewPassword())) {
                //TODO: Throw password do not match exception!

                throw new Exception("Passwords do not match!");
            } else {
                throw new NoSuchElementException("User with username = " + username + " not found." + buyer);
            }
        }
    }
}
