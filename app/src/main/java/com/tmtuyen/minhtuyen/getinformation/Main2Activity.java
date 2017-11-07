package com.tmtuyen.minhtuyen.getinformation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.vlk.multimager.utils.Image;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Main2Activity extends AppCompatActivity {
    @InjectView(R.id.btnLocate)
    ImageButton btnLocation;
    @InjectView(R.id.btnTakePicture)
    ImageButton btnTakePicture;
    @InjectView(R.id.spinner_nav)
    Spinner spinner;
    @InjectView(R.id.edtViDo)
    EditText edtKinhDo;
    @InjectView(R.id.edtKinhDo)
    EditText edtViDo;
    @InjectView(R.id.edtXPHT)
    EditText edtAddress;
    @InjectView(R.id.edtTenDuong)
    EditText edtTenDuong;
    @InjectView(R.id.edtHinhAnh)
    EditText edtAlbum;
    @InjectView(R.id.edtName)
    EditText edtName;
    @InjectView(R.id.edtSoNha)
    EditText edtSoNha;
    @InjectView(R.id.edtApKhom)
    EditText edtApKhom;
    @InjectView(R.id.edtDienThoai)
    EditText edtDienThoai;
    @InjectView(R.id.edtFax)
    EditText edtFax;
    @InjectView(R.id.edtEmail)
    EditText edtEmail;
    @InjectView(R.id.edtWebsite)
    EditText edtWebsite;
    @InjectView(R.id.linearLayout2)
    LinearLayout rootLayout;
    @InjectView(R.id.edtMoTa)
    EditText edtMoTa;
    @InjectView(R.id.swUpload)
    Switch aSwitch;
    EditText edtNguoiLienHe, edtHangSao, edtTongPhong, edtGiaThap, edtGioMoCua, edtMonNgon;
    Spinner spnPhanLoai, spnXepHang;
    CheckBox chkPhongHN, chkNhaHang, chkBar, chkGym, chkHoBoi, chkKaraoke, chkMassage, chkTenis;

    String tenDiaDiem, apKhom, xaPhuong, huyenTP, soDuong, tenDuong, dienThoai, fax, email, website,
            nguoiLienLac, gioMoCua, giaPhong, monNgon, moTa;
    ArrayList<Image> imagesList;
    int idUser, idDiaDiem, xepHang, phanLoai, hangSao, soPhong,
            hoBoi, karaoke, message, gym, nhaHang, bar, phongHN, tenis, preLayout;
    double kinhDo, viDo;
    String server;

    private void prepareParams() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyData", 0);
        idUser = pref.getInt("userID", 0);
        idDiaDiem = spinner.getSelectedItemPosition();
        tenDiaDiem = edtName.getText().toString();
        soDuong = edtSoNha.getText().toString();
        tenDuong = edtTenDuong.getText().toString();
        apKhom = edtApKhom.getText().toString();
        dienThoai = edtDienThoai.getText().toString();
        fax = edtFax.getText().toString();
        email = edtEmail.getText().toString();
        website = edtWebsite.getText().toString();
        moTa = edtMoTa.getText().toString();
        if (edtNguoiLienHe != null) nguoiLienLac = edtNguoiLienHe.getText().toString();
        if (edtGioMoCua != null) gioMoCua = edtGioMoCua.getText().toString();
        if (edtMonNgon != null) monNgon = edtMonNgon.getText().toString();
        if (spnXepHang != null) xepHang = spnXepHang.getSelectedItemPosition();
        if (spnPhanLoai != null) phanLoai = spnPhanLoai.getSelectedItemPosition();
        if (edtHangSao != null && !edtHangSao.getText().toString().isEmpty())
            hangSao = Integer.parseInt(edtHangSao.getText().toString());
        if (edtTongPhong != null && !edtTongPhong.getText().toString().isEmpty())
            soPhong = Integer.parseInt(edtTongPhong.getText().toString());
        int giaThap = 0;
        if (edtGiaThap != null && !edtGiaThap.getText().toString().isEmpty())
            giaThap = Integer.parseInt(edtGiaThap.getText().toString());
        giaPhong = giaThap + "";
        if (chkHoBoi != null) hoBoi = chkHoBoi.isChecked() ? 1 : 0;
        if (chkNhaHang != null) nhaHang = chkNhaHang.isChecked() ? 1 : 0;
        if (chkGym != null) gym = chkGym.isChecked() ? 1 : 0;
        if (chkBar != null) bar = chkBar.isChecked() ? 1 : 0;
        if (chkMassage != null) message = chkMassage.isChecked() ? 1 : 0;
        if (chkTenis != null) tenis = chkTenis.isChecked() ? 1 : 0;
        if (chkPhongHN != null) phongHN = chkPhongHN.isChecked() ? 1 : 0;
        if (chkKaraoke != null) karaoke = chkKaraoke.isChecked() ? 1 : 0;
    }

    private void initParams() {
        tenDiaDiem = "";
        apKhom = "";
        xaPhuong = "";
        huyenTP = "";
        soDuong = "";
        tenDuong = "";
        dienThoai = "";
        fax = "";
        email = "";
        website = "";
        nguoiLienLac = "";
        gioMoCua = "";
        giaPhong = "";
        monNgon = "";
        moTa = "";
        preLayout = 0;
        idUser = 0;
        hangSao = 0;
        soPhong = 0;
        idDiaDiem = 0;
        xepHang = 0;
        phanLoai = 0;
        kinhDo = 0.0;
        viDo = 0.0;
        hoBoi = 0;
        karaoke = 0;
        message = 0;
        gym = 0;
        nhaHang = 0;
        bar = 0;
        phongHN = 0;
        tenis = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyData", 0);
        server = pref.getString("server", "http://api.agtravellive.com");
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                    case 2:
                    case 5:
                    case 3:
                    case 10:
                    case 11:
                    case 12:
                        rootLayout.removeAllViews();
                        break;
                    case 6:
                        rootLayout.removeAllViews();
                        addLayout(R.layout.nguoi_lien_lac);
                        edtNguoiLienHe = (EditText) findViewById(R.id.edtNguoiLienLac);
                        edtGioMoCua = (EditText) findViewById(R.id.edtGioMoCua);
                        break;
                    case 4:
                        rootLayout.removeAllViews();
                        addLayout(R.layout.cs_luu_tru);
                        edtNguoiLienHe = (EditText) findViewById(R.id.edtNguoiLienLac);
                        edtGioMoCua = (EditText) findViewById(R.id.edtGioMoCua);
                        edtHangSao = (EditText) findViewById(R.id.edtHangSao);
                        edtTongPhong = (EditText) findViewById(R.id.edtSoPhong);
                        edtGiaThap = (EditText) findViewById(R.id.edtGiaMin);
                        chkBar = (CheckBox) findViewById(R.id.cbBar);
                        chkGym = (CheckBox) findViewById(R.id.cbGym);
                        chkHoBoi = (CheckBox) findViewById(R.id.cbHoBoi);
                        chkKaraoke = (CheckBox) findViewById(R.id.cbKaraoke);
                        chkMassage = (CheckBox) findViewById(R.id.cbMassage);
                        chkNhaHang = (CheckBox) findViewById(R.id.cbNhaHang);
                        chkPhongHN = (CheckBox) findViewById(R.id.cbPhongHoiNghi);
                        chkTenis = (CheckBox) findViewById(R.id.cbTenis);
                        break;
                    case 7:
                        rootLayout.removeAllViews();
                        addLayout(R.layout.am_thuc);
                        edtNguoiLienHe = (EditText) findViewById(R.id.edtNguoiLienLac);
                        edtGioMoCua = (EditText) findViewById(R.id.edtGioMoCua);
                        edtMonNgon = (EditText) findViewById(R.id.edtMonNgon);
                        break;
                    case 8:
                        rootLayout.removeAllViews();
                        addLayout(R.layout.diem_du_lich);
                        edtNguoiLienHe = (EditText) findViewById(R.id.edtNguoiLienLac);
                        edtGioMoCua = (EditText) findViewById(R.id.edtGioMoCua);
                        spnPhanLoai = (Spinner) findViewById(R.id.spnPhanLoai);
                        spnXepHang = (Spinner) findViewById(R.id.spnXepHang);
                        break;
                    case 9:
                        rootLayout.removeAllViews();
                        addLayout(R.layout.nguoi_lien_lac);
                        edtNguoiLienHe = (EditText) findViewById(R.id.edtNguoiLienLac);
                        edtGioMoCua = (EditText) findViewById(R.id.edtGioMoCua);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                startActivityForResult(intent, 1);
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putParcelableArrayListExtra("album", imagesList);
                startActivityForResult(intent, 2);
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    Toast.makeText(Main2Activity.this, "Hình sẽ được upload sau!", Toast.LENGTH_LONG).show();
            }
        });
        imagesList = new ArrayList<>();
        initParams();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            viDo = data.getDoubleExtra("lat", 0);
            edtViDo.setText(viDo + "");
            kinhDo = data.getDoubleExtra("log", 0);
            edtKinhDo.setText(kinhDo + "");
            edtTenDuong.setText(data.getStringExtra("tenduong"));
            xaPhuong = data.getStringExtra("tenxaphuong");
            huyenTP = data.getStringExtra("tenthitrantp");
            edtAddress.setText(xaPhuong + ", " + huyenTP);
        }
        if (requestCode == 2) {
            imagesList = data.getParcelableArrayListExtra("album");
            if (!imagesList.isEmpty())
                edtAlbum.setText("Album có " + imagesList.size() + " ảnh");
            else
                edtAlbum.setText("Album chưa có ảnh!");
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyData", 0);
        switch (item.getItemId()) {
            case R.id.mnSend:
                prepareParams();
                if (!validate()) return false;
                postData();
                break;
            case R.id.mnLogout:
                pref.edit().clear().commit();
                deleteFile("dsHinh.tmt");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.mnSync:
                ArrayList<String> arrayList = new ArrayList<>();
                try {
                    FileInputStream fIn = openFileInput("dsHinh.tmt");
                    InputStreamReader reader = new InputStreamReader(fIn);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String receiveString = "";
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        arrayList.add(receiveString);
                    }

                    String[] split = arrayList.get(0).split(";");
                    String currentID = split[0];
                    String currentType = split[1];
                    int i = 0;
                    while (i < arrayList.size()) {
                        ArrayList<String> photos = new ArrayList<>();
                        do {
                            split = arrayList.get(i).split(";");
                            photos.add(split[2]);
                            i++;
                        }
                        while (i < arrayList.size() && (currentID.contentEquals(arrayList.get(i).split(";")[0]))
                                && (currentType.contentEquals(arrayList.get(i).split(";")[1])));
                        sync("str", "id:" + currentID + ",id_loai:" + currentType, photos.toArray(new String[0]));
                        if (i < arrayList.size()) {
                            currentID = arrayList.get(i).split(";")[0];
                            currentType = arrayList.get(i).split(";")[1];
                        }
                    }
                    fIn.close();
                    deleteFile("dsHinh.tmt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    private void postData() {
        String str = "id_user:" + idUser + ",id_xep_hang:" + xepHang + ",id_phanloai:" + phanLoai +
                ",id_loai:" + idDiaDiem + ",ten_dia_diem:" + tenDiaDiem + ",kinh_do:" + kinhDo + ",vi_do:" + viDo +
                ",ap_khom:" + apKhom + ",xa_phuong:" + xaPhuong + ",huyen_thi:" + huyenTP +
                ",so_duong:" + soDuong + ",ten_duong:" + tenDuong + ",mo_ta:" + moTa + ",dien_thoai:" + dienThoai +
                ",fax:" + fax + ",email:" + email + ",website:" + website + ",nguoi_lien_lac:" + nguoiLienLac + ",gio_mo_cua:" + gioMoCua +
                ",hang_sao:" + hangSao + ",so_phong:" + soPhong + ",gia_phong:" + giaPhong + ",ho_boi:" + hoBoi +
                ",karaoke:" + karaoke + ",massage:" + message + ",gym:" + gym + ",nha_hang:" + nhaHang + ",bar:" + bar +
                ",phong_hoi_nghi:" + phongHN + ",tennis:" + tenis + ",mon_ngon:" + monNgon;
        String[] album = new String[imagesList.size()];
        for (int i = 0; i < album.length; i++)
            album[i] = imagesList.get(i).imagePath;
        upload("str", str, album);
    }

    private void addLayout(int layout) {
        preLayout = layout;
        View hiddenInfo = getLayoutInflater().inflate(layout, rootLayout, false);
        rootLayout.addView(hiddenInfo);
    }

    private void reloadLayout() {
        finish();
        initParams();
        startActivity(getIntent());
    }

    public boolean validate() {
        boolean valid = true;
        if (tenDiaDiem.isEmpty()) {
            edtName.setError("Không được để trống.");
            valid = false;
        } else {
            edtName.setError(null);
        }

        if (kinhDo == 0 || viDo == 0) {
            edtKinhDo.setError("Lấy Vĩ độ");
            edtViDo.setError("Lấy Kinh độ");
            valid = false;
        } else {
            edtKinhDo.setError(null);
        }
        /*if (imagesList.isEmpty() || imagesList.size() < 1) {
            edtAlbum.setError("Ít nhất phải có 1 ảnh");
            valid = false;
        } else
            edtAlbum.setError(null);*/
        if (idDiaDiem == 0) {
            Toast.makeText(this, "Vui lòng chọn đối tượng khảo sát", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    private void upload(String key, String value, String[] album) {
        final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang gởi dữ liệu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            final MultipartUploadRequest request =
                    new MultipartUploadRequest(this, server + "/api/diadiem/them")
                            .setMethod("POST")
                            .setUtf8Charset()
                            .setMaxRetries(3)
                            .addParameter(key, value)
                            .setUsesFixedLengthStreamingMode(true);
            if (album.length > 0 && aSwitch.isChecked())
            for (int i = 0; i < album.length; i++) {
                request.addFileToUpload(album[i], "photos[]");
            }
            else {
                request.addParameter("photos", "null");
                if (album.length > 0) {
                    try {
                        FileOutputStream fOut = openFileOutput("dsTam.tmt", MODE_PRIVATE | MODE_APPEND);
                        OutputStreamWriter writer = new OutputStreamWriter(fOut);
                        for (int i = 0; i < album.length; i++) {
                            writer.write(album[i] + "\n");
                        }
                        writer.flush();
                        writer.close();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            request.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {

                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    Toast.makeText(getApplicationContext(), "Gởi dữ liệu thất bại!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    String resp = serverResponse.getBodyAsString();
                    if (resp.matches("\\d+(?:\\.\\d+)?") && !resp.contentEquals("-1")) {
                        try {
                            FileInputStream fIn = openFileInput("dsTam.tmt");
                            InputStreamReader reader = new InputStreamReader(fIn);
                            BufferedReader bufferedReader = new BufferedReader(reader);
                            String receiveString = "";
                            FileOutputStream fOut = openFileOutput("dsHinh.tmt", MODE_PRIVATE | MODE_APPEND);
                            OutputStreamWriter writer = new OutputStreamWriter(fOut);
                            while ((receiveString = bufferedReader.readLine()) != null) {
                                writer.write(resp + ";" + idDiaDiem + ";" + receiveString + "\n");
                            }
                            writer.flush();
                            writer.close();
                            fOut.close();
                            fIn.close();
                            deleteFile("dsTam.tmt");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Thành công. Nhập điểm mới.", Toast.LENGTH_LONG).show();
                        reloadLayout();
                        initParams();
                    } else
                        Toast.makeText(getApplicationContext(), "Thất bại. Vui lòng gởi lại...", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {

                }
            });
            request.startUpload();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void sync(String key, String value, String[] photos) {
        final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang đồng bộ ảnh...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            final MultipartUploadRequest request = new MultipartUploadRequest(this, server + "/api/diadiem/dongbo")
                    .setMethod("POST")
                    .setUtf8Charset()
                    .setMaxRetries(3)
                    .addParameter(key, value)
                    .setUsesFixedLengthStreamingMode(true);
            for (int i = 0; i < photos.length; i++) {
                request.addFileToUpload(photos[i], "photos[]");
            }
            request.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {

                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    Toast.makeText(getApplicationContext(), "FALSE", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    String resp = serverResponse.getBodyAsString();
                    if (resp.contentEquals("1")) {
                        Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(getApplicationContext(), "FALSE", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {

                }
            });
            request.startUpload();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}