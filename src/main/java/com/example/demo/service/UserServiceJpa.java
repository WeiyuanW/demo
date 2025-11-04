package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Spring Data JPA implementation
 *
 * This is the simplest service implementation as Spring Data JPA's
 * JpaRepository already provides all the basic CRUD operations.
 * The service layer here is very thin, but it's still important to have
 * for future business logic and to keep the controller decoupled from
 * the repository layer.
 */
@Service("userServiceJpa")
@Transactional
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;
    private final DistributedLockService lockService;

    public UserServiceJpa(UserRepository userRepository, DistributedLockService lockService) {
        this.userRepository = userRepository;
        this.lockService = lockService;
    }

    @Override
    public User createUser(User user) {
        // JpaRepository.save() handles both insert and update
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        // findById is provided by JpaRepository
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // findAll is provided by JpaRepository
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
        // save() handles both create and update
        return userRepository.save(user);
    }

    // 乐观锁更新方法 (Optimistic Lock)
    @Transactional
    public User updateOptimistic(User userUpdateData) {
        String lockKey = "user:lock:" + userUpdateData.getId(); // 🔑 针对同ID的用户做分布式锁

        return lockService.executeWithLock(lockKey, () -> {
            // **查找 (Read):** 使用标准的 findById
            // 此时数据行未被锁定
            User existingUser = userRepository.findById(userUpdateData.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // **业务逻辑:** 更新字段
            existingUser.setFirstName(userUpdateData.getFirstName());
            existingUser.setLastName(userUpdateData.getLastName());
            existingUser.setEmail(userUpdateData.getEmail());

            // **保存 (Write):** save() 方法执行 UPDATE 语句时，
            // 会自动在 WHERE 子句中包含 version 字段。
            // 如果 version 不匹配，将抛出 ObjectOptimisticLockingFailureException。
            return userRepository.save(existingUser);
        });
    }

    // 悲观锁更新方法 (Pessimistic Lock)
    @Transactional
    public User updatePessimistic(User userUpdateData) {
        String lockKey = "user:lock:" + userUpdateData.getId();// 同一用户统一锁 key

        return lockService.executeWithLock(lockKey, () -> {
            // **查找 (Read):** 使用 @Lock 标记的 findByIdForUpdate
            // 此时数据库会执行 SELECT ... FOR UPDATE，锁定该行数据。
            User existingUser = userRepository.findByIdForUpdate(userUpdateData.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // **业务逻辑:** 更新字段 (此时是安全的，因为数据已被锁定)
            existingUser.setFirstName(userUpdateData.getFirstName());
            existingUser.setLastName(userUpdateData.getLastName());
            existingUser.setEmail(userUpdateData.getEmail());
            // ... (更新其他字段)

            // **保存 (Write):** 事务提交时，锁会被释放。
            return userRepository.save(existingUser);
        });
    }


    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        // deleteById is provided by JpaRepository
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(Long id) {
        // existsById is provided by JpaRepository
        return userRepository.existsById(id);
    }
}
