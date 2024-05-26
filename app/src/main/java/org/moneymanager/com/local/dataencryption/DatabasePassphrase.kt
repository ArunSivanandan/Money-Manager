package org.moneymanager.com.local.dataencryption

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import java.io.File
import java.security.SecureRandom

class DatabasePassphrase (private val mContext: Context) {

    fun getPassphrase(): ByteArray{

        // An EncryptedFile instance is created using the AndroidX Security library.
        // It uses the AES256_GCM_SPEC to create or retrieve a master key for encryption.
        // The encryption scheme is set to AES256_GCM_HKDF_4KB.
        // The file is specified as user_passphrase.bin.
        val file = File(mContext.filesDir, "db_passphrase.bin")
        val encryptedFile = EncryptedFile.Builder(
            file,
            mContext,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return if(file.exists()){
            encryptedFile.openFileInput().use { it.readBytes() }
        } else {
            generatePassphrase().also {
                    passPhrase ->
                encryptedFile.openFileOutput().use { it.write(passPhrase) }
            }
        }
    }

    /**
     * This private function generates a random 32-byte passphrase using a SecureRandom instance.
     * It ensures that the generated passphrase doesn't contain any null bytes (0) to avoid potential issues.
     * **/
    private fun generatePassphrase(): ByteArray{
        // The SecureRandom instance is used to generate a cryptographically secure random passphrase.
        val random = SecureRandom.getInstanceStrong()
        val result = ByteArray(32)

        random.nextBytes(result)
        while (result.contains(0)){
            random.nextBytes(result)
        }

        return result
    }
}