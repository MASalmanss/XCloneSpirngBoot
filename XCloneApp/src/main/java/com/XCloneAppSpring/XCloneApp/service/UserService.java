package com.XCloneAppSpring.XCloneApp.service;

import com.XCloneAppSpring.XCloneApp.dto.request.UserCreateDto;
import com.XCloneAppSpring.XCloneApp.dto.request.UserUpdateDto;
import com.XCloneAppSpring.XCloneApp.dto.response.UserResource;
import com.XCloneAppSpring.XCloneApp.entity.Users;
import com.XCloneAppSpring.XCloneApp.mappers.UsersMapper;
import com.XCloneAppSpring.XCloneApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;

    public Users createUser(UserCreateDto userCreateDto){
        var user = usersMapper.userDtoToUsers(userCreateDto);
        user.set_active(true);
        userRepository.save(user);
        return user;
    }

    public Users getByIdUser(UUID id){
        var users = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found !"));
        if(users.is_active()){
            return users;
        }
        throw new RuntimeException("User not found !");
    }

    public Users updateUserById(UserUpdateDto userUpdateDto){
        Users users = userRepository.findById(userUpdateDto.getId()).orElseThrow(()-> new RuntimeException("User not found !"));
        users.setDescription(userUpdateDto.getDescription());
        users.setFullname(userUpdateDto.getFullname());
        return userRepository.save(users);
    }

    public void followUser(UUID userId , UUID followerId){
        Users user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found !"));
        Users follower = userRepository.findById(followerId).orElseThrow(()-> new RuntimeException("Fallower not found"));

        user.getFollowers().add(follower);
        userRepository.save(user);
    }

    public void unFollowUser(UUID userId , UUID followerId){
        Users user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found !"));
        Users follower = userRepository.findById(followerId).orElseThrow(()-> new RuntimeException("Fallower not found"));
        user.getFollowers().remove(follower);
        userRepository.save(user);
    }

    public List<Users> getFollowings(UUID id){
        Users users = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found !"));
        List<Users> followings = users.getFollowing();
        return followings;
    }

    public List<Users> getFollowers(UUID id){
        Users users = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found !"));
        List<Users> followers = users.getFollowers();
        return followers;
    }
}
