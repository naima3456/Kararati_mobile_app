package com.example.project

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_arret.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*


class arretActivity : AppCompatActivity() {
     lateinit var arretDao: arretDao
    private lateinit var arret:arret_complet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arret)

        // init offline Dao Database
        arretDao = AppDatabase.buildDatabase(this)?.getArretDao()!!


        val bundle : Bundle?= intent.extras

        val num = bundle!!.getInt("numArret")

        arret = arretDao.getArretById(num)

         num_decision.text = arret.num_arret.toString()
        numP_ref_decision.text = arret.numRef_arret
         chambre_decision.text =  arret.chambre_arret
        val chambre_decision =  arret.chambre_arret
        date_decision.text =  arret.date_arret
        chambre_decision_2.text =  arret.chambre_arret
        reference_decision.text =  arret.reference_arret
        parties_decision.text = arret.parties_arret
        ref_decision.text = arret.reference_arret
        num_ref_decision.text = arret.numRef_arret


        annee_decision.text = arret.annee_arret
        numP_ref_decision.text = arret.numPageRef_arret
        principe_decision.text = arret.principe_arret
        decision_t.text= arret.decision_arret
        decisionOp_t.text = arret.decisionOper_arret
        composition_t.text = arret.composition_arret
        val date_decision =  arret.date_arret
        val chambre_decision_2 =  arret.chambre_arret
        val reference_decision =  arret.reference_arret
        val parties_decision = arret.parties_arret
        val ref_decision = arret.reference_arret
        val annee_decision = arret.annee_arret
        val numP_ref_decision = arret.numPageRef_arret
        val principe_decision = arret.principe_arret
        val decision_t= arret.decision_arret
        val decisionOp_t = arret.decisionOper_arret
        val composition_t = arret.composition_arret
        shareBtn.setOnClickListener{

            val subject = "القرار رقم  "+num.toString()+" "+"الصادر عن  "+chambre_decision+" "+" بتاريخ  "+" "+date_decision
            val message = "أطراف القضية : "+ parties_decision +"\n"+"المرجع :  " + reference_decision + "  رقم  "+ num_ref_decision+"\n"+ "  لسنة "+annee_decision+"\n"+"الصفحة: "+numP_ref_decision+"\n"+" المبدأ: "+"\n"+ principe_decision + " القرار: "+"\n"+decision_t+"\n"+" منطوق القرار: "+"\n"+decisionOp_t+"\n"+" تشكيلة المجلس:   "+"\n"+composition_t

//            val intent_1 = Intent(Intent.ACTION_SENDTO).apply {
//
//                data = Uri.parse("mailto:")
//                putExtra(Intent.EXTRA_EMAIL," ")
//                putExtra(Intent.EXTRA_SUBJECT, subject)
//                putExtra(Intent.EXTRA_TEXT,message)
//
//            }
//
//            val chooser = Intent.createChooser(intent_1, "share using")
//
//            if (intent_1.resolveActivity(packageManager) != null){
//                startActivity(chooser)
//
//            }else{
//
//                Toast.makeText(this@arretActivity,"Required App is not installed", Toast.LENGTH_SHORT).show()
//
//            }
//

            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(" "))
            i.putExtra(Intent.EXTRA_SUBJECT, subject)
            i.putExtra(Intent.EXTRA_TEXT, message)
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
//                Toast.makeText(
//                    this@arretActivity,
//                    "There are no email clients installed.",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }
        //SMS

        shareBtnMsg.setOnClickListener {
            val requestCode = 123
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                val contact_picker = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                startActivityForResult(contact_picker,requestCode)}
            else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 123 )
            }

        }




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123 && resultCode == Activity.RESULT_OK){
            var contacturi: Uri = data?.data ?: return
            var cols:Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var rs: Cursor? = contentResolver.query(contacturi,cols,null,null,null)

            if(rs?.moveToFirst()!!){
                val numero = rs.getString(0)
                val bundle : Bundle?= intent.extras
                val num = bundle!!.getInt("numArret")
                arret = arretDao.getArretById(num)

                val chambre = arret.chambre_arret
                val date = arret.date_arret

                val message =  "القرار رقم  "+num+" "+"الصادر عن  "+chambre+" "+" بتاريخ  "+" "+date



                var sms = SmsManager.getDefault()
                sms.sendTextMessage(numero.toString(), null, message, null, null)
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, " تم إرسال الرسالة بنجاح!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, " لم يتم إرسال الرسالة!", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, " لم تختر مستلمًا!", Toast.LENGTH_SHORT).show()

        }


    }
}