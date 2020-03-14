package com.tranquocdai.freshmarket.bootstrap;

import com.tranquocdai.freshmarket.model.*;
import com.tranquocdai.freshmarket.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository adminRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RoleResponsitory roleResponsitory;

    @Autowired
    private CalculationUnitRepository calculationUnitRepository;

    @Autowired
    private TypePostRepository typePostRepository;



    @Override
    public void run(String... args) throws Exception {
        //add admin
        User admin = new User();
        admin.setUserName("admin");
        // pass = secret123
        admin.setPassword("$2a$10$f7RMh3epXApK615P84.VpO.ElgRgkBXwba1rph974t6ur6QfAtGZG");
        admin.setFullName("admin");
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleID(1L);
        roleUser.setRoleName("admin");
        roleResponsitory.save(roleUser);
        admin.setRoleUser(roleUser);
        adminRepository.save(admin);

        //add role
        RoleUser dn=new RoleUser();
        dn.setRoleName("Doanh nghiệp");
        roleResponsitory.save(dn);

        RoleUser htx=new RoleUser();
        htx.setRoleName("Hợp tác xã");
        roleResponsitory.save(htx);

        RoleUser nd=new RoleUser();
        nd.setRoleName("Nông dân");
        roleResponsitory.save(nd);

        RoleUser ntd=new RoleUser();
        ntd.setRoleName("Người tiêu dùng");
        roleResponsitory.save(nd);

        RoleUser diff=new RoleUser();
        diff.setRoleName("khác");
        roleResponsitory.save(diff);

        //add type post
        TypePost sell= new TypePost();
        sell.setTypePostName("Sell");
        typePostRepository.save(sell);

        TypePost buy= new TypePost();
        buy.setTypePostName("Buy");
        typePostRepository.save(buy);

        //add calculationUnit
        CalculationUnit kg=new CalculationUnit();
        kg.setUnitName("Kg");
        calculationUnitRepository.save(kg);

        CalculationUnit gram=new CalculationUnit();
        gram.setUnitName("Gram");
        calculationUnitRepository.save(gram);

        CalculationUnit hop=new CalculationUnit();
        hop.setUnitName("Hộp");
        calculationUnitRepository.save(hop);

        CalculationUnit sp=new CalculationUnit();
        sp.setUnitName("Sản Phẩm");
        calculationUnitRepository.save(sp);

        CalculationUnit qua=new CalculationUnit();
        qua.setUnitName("Quả");
        calculationUnitRepository.save(qua);
        //add province
        Province DaN = new Province();
        DaN.setNameProvince("Đà Nẵng");
        provinceRepository.save(DaN);

        Province HP = new Province();
        HP.setNameProvince("Hải Phòng");
        provinceRepository.save(HP);

        Province HaN = new Province();
        HaN.setNameProvince("Hà Nội");
        provinceRepository.save(HaN);

        Province HCM = new Province();
        HCM.setNameProvince("TP HCM");
        provinceRepository.save(HCM);

        Province AG = new Province();
        AG.setNameProvince("An Giang");
        provinceRepository.save(AG);

        Province BV = new Province();
        BV.setNameProvince("Bà Rịa - Vũng Tàu");
        provinceRepository.save(BV);

        Province BG = new Province();
        BG.setNameProvince("Bắc Giang");
        provinceRepository.save(BG);

        Province BK = new Province();
        BK.setNameProvince("Bắc Kạn");
        provinceRepository.save(BK);

        Province BL = new Province();
        BL.setNameProvince("Bạc Liêu");
        provinceRepository.save(BL);

        Province BN = new Province();
        BN.setNameProvince("Bắc Ninh");
        provinceRepository.save(BN);

        Province BT = new Province();
        BT.setNameProvince("Bến Tre");
        provinceRepository.save(BT);

        Province BD = new Province();
        BD.setNameProvince("Bình Định");
        provinceRepository.save(BD);

        Province BDu = new Province();
        BDu.setNameProvince("Bình Dương");
        provinceRepository.save(BDu);

        Province BP = new Province();
        BP.setNameProvince("Bình Phước");
        provinceRepository.save(BP);

        Province BTh = new Province();
        BTh.setNameProvince("Bình Thuận");
        provinceRepository.save(BTh);

        Province CM = new Province();
        CM.setNameProvince("Cà Mau");
        provinceRepository.save(CM);

        Province CB = new Province();
        CB.setNameProvince("Cao Bằng");
        provinceRepository.save(CB);

        Province DL = new Province();
        DL.setNameProvince("Đắk Lắk");
        provinceRepository.save(DL);

        Province DN = new Province();
        DN.setNameProvince("Đắk Nông");
        provinceRepository.save(DN);

        Province DB = new Province();
        DB.setNameProvince("Điện Biên");
        provinceRepository.save(DB);

        Province DNa = new Province();
        DNa.setNameProvince("Đồng Nai");
        provinceRepository.save(DNa);

        Province DT = new Province();
        DT.setNameProvince("Đồng Tháp");
        provinceRepository.save(DT);

        Province GL = new Province();
        GL.setNameProvince("Gia Lai");
        provinceRepository.save(GL);

        Province HG = new Province();
        HG.setNameProvince("Hà Giang");
        provinceRepository.save(HG);

        Province HN = new Province();
        HN.setNameProvince("Hà Nam");
        provinceRepository.save(HN);

        Province HT = new Province();
        HT.setNameProvince("Hà Tĩnh");
        provinceRepository.save(HT);

        Province HD = new Province();
        HD.setNameProvince("Hải Dương");
        provinceRepository.save(HD);

        Province HGi = new Province();
        HGi.setNameProvince("Hậu Giang");
        provinceRepository.save(HGi);

        Province HB = new Province();
        HB.setNameProvince("Hòa Bình");
        provinceRepository.save(HB);

        Province HY = new Province();
        HY.setNameProvince("Hưng Yên");
        provinceRepository.save(HY);

        Province KH = new Province();
        KH.setNameProvince("Khánh Hòa");
        provinceRepository.save(KH);

        Province KG = new Province();
        KG.setNameProvince("Kiên Giang");
        provinceRepository.save(KG);

        Province KT = new Province();
        KT.setNameProvince("Kon Tum");
        provinceRepository.save(KT);

        Province LC = new Province();
        LC.setNameProvince("Lai Châu");
        provinceRepository.save(LC);

        Province LD = new Province();
        LD.setNameProvince("Lâm Đồng");
        provinceRepository.save(LD);

        Province LS = new Province();
        LS.setNameProvince("Lạng Sơn");
        provinceRepository.save(LS);

        Province LCa = new Province();
        LCa.setNameProvince("Lào Cai");
        provinceRepository.save(LCa);

        Province LA = new Province();
        LA.setNameProvince("Long An");
        provinceRepository.save(LA);

        Province ND = new Province();
        ND.setNameProvince("Nam Định");
        provinceRepository.save(ND);

        Province NA = new Province();
        NA.setNameProvince("Nghệ An");
        provinceRepository.save(NA);

        Province NB = new Province();
        NB.setNameProvince("Ninh Bình");
        provinceRepository.save(NB);

        Province NT = new Province();
        NT.setNameProvince("Ninh Thuận");
        provinceRepository.save(NT);

        Province PT = new Province();
        PT.setNameProvince("Phú Thọ");
        provinceRepository.save(PT);

        Province QB = new Province();
        QB.setNameProvince("Quảng Bình");
        provinceRepository.save(QB);

        Province QNa = new Province();
        QNa.setNameProvince("Quảng Nam");
        provinceRepository.save(QNa);

        Province QN = new Province();
        QN.setNameProvince("Quảng Ngãi");
        provinceRepository.save(QN);

        Province QNi = new Province();
        QNi.setNameProvince("Quảng Ninh");
        provinceRepository.save(QNi);

        Province QTr = new Province();
        QTr.setNameProvince("Quảng Trị");
        provinceRepository.save(QTr);

        Province ST = new Province();
        ST.setNameProvince("Sóc Trăng");
        provinceRepository.save(ST);

        Province SL = new Province();
        SL.setNameProvince("Sơn La");
        provinceRepository.save(SL);

        Province TN = new Province();
        TN.setNameProvince("Tây Ninh");
        provinceRepository.save(TN);

        Province TB = new Province();
        TB.setNameProvince("Thái Bình");
        provinceRepository.save(TB);

        Province TNg = new Province();
        TNg.setNameProvince("Thái Nguyên");
        provinceRepository.save(TNg);

        Province TH = new Province();
        TH.setNameProvince("Thanh Hóa");
        provinceRepository.save(TH);

        Province TT = new Province();
        TT.setNameProvince("Thừa Thiên Huế");
        provinceRepository.save(TT);

        Province TG = new Province();
        TG.setNameProvince("Tiền Giang");
        provinceRepository.save(TG);

        Province TV = new Province();
        TV.setNameProvince("Trà Vinh");
        provinceRepository.save(TV);

        Province TQ = new Province();
        TQ.setNameProvince("Tuyên Quang");
        provinceRepository.save(TQ);

        Province VL = new Province();
        VL.setNameProvince("Vĩnh Long");
        provinceRepository.save(VL);

        Province VP = new Province();
        VP.setNameProvince("Vĩnh Phúc");
        provinceRepository.save(VP);

        Province YB = new Province();
        YB.setNameProvince("Yên Bái");
        provinceRepository.save(YB);

        Province PY = new Province();
        PY.setNameProvince("Phú Yên");
        provinceRepository.save(PY);

        Province CT = new Province();
        CT.setNameProvince("Cần Thơ");
        provinceRepository.save(CT);


        //add place categories
        Category category1 = new Category();
        category1.setNameCategory("Nông nghiệp");

        Category category2 = new Category();
        category2.setNameCategory("Thủy_Hải Sản");

        Category category3 = new Category();
        category3.setNameCategory("Chăn nuôi");

        Category category4 = new Category();
        category4.setNameCategory("Hạt,cây giống");

        Category category5 = new Category();
        category5.setNameCategory("Vật tư");

        Category category6 = new Category();
        category6.setNameCategory("Thức ăn chăn nuôi");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);
    }
}
