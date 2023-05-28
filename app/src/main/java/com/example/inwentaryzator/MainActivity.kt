package com.example.inwentaryzator
import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var kod:EditText
        kod = findViewById<EditText>(R.id.KodProduktu)
        lateinit var cena:EditText
        cena = findViewById<EditText>(R.id.Cena)
        lateinit var ilosc:EditText
        ilosc = findViewById<EditText>(R.id.Ilosc)


        fun OutputStream.writeCsv()
        {
            val writer = bufferedWriter()
            //nagłówek pliku
            writer.write("\"Kod towaru\",\"Cena netto\",\"Ilość\"")

        val barcodeLauncher = registerForActivityResult(ScanContract())
        {
           result: ScanIntentResult ->
            if (result.contents == null)
           {
                Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_LONG).show()
            }
            else
            {
                kod.setText(result.contents)
            }
        }

        val btnScan:Button = findViewById(R.id.button)
        btnScan.setOnClickListener{
            barcodeLauncher.launch(ScanOptions())
        }
            val btnDodaj:Button = findViewById(R.id.Dodaj)
            btnDodaj.setOnClickListener{
                //zawartość
                writer.newLine()
                val text = "\""+kod.text.toString()+"\", \""+cena.text.toString()+"\", \""+ilosc.text.toString()+"\""
                writer.write(text)
                writer.flush()
            }

        }



        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        this.requestPermissions(permissions, 5)
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(path, "/filename.csv")
        FileOutputStream(file).apply { writeCsv() }
    }
}