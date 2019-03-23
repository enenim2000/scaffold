package com.enenim.scaffold.util;

import com.enenim.scaffold.interfaces.IPasswordEncoder;

import static com.kosprov.jargon2.api.Jargon2.*;

public class Jargon2PasswordEncoder implements IPasswordEncoder{

    @Override
    public String encode(String rawPassword) {
        // Configure the hasher
        Hasher hasher = jargon2Hasher()
                .type(Type.ARGON2d) // Data-dependent hashing
                .memoryCost(65536)  // 64MB memory cost
                .timeCost(3)        // 3 passes through memory
                .parallelism(4)     // use 4 lanes and 4 threads
                .saltLength(16)     // 16 random bytes salt
                .hashLength(16);    // 16 bytes output hash

        // Set the password and calculate the encoded hash
        return hasher.password(rawPassword.getBytes()).encodedHash();
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        // Just get a hold on the verifier. No special configuration needed
        Verifier verifier = jargon2Verifier();

        // Set the encoded hash, the password and verify
        return verifier.hash(encodedPassword).password(rawPassword.getBytes()).verifyEncoded();
    }
}
