package com.lcaohoanq.views.user.register;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.enums.UserStatusEnum;
import com.lcaohoanq.models.Role;
import com.lcaohoanq.models.Status;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.PayloadUtils;
import com.lcaohoanq.utils.StringUtils;
import com.lcaohoanq.utils.ValidationUtils;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.schemas.UserRegisterRequest;
import com.lcaohoanq.views.base.RegisterPage;
import com.lcaohoanq.views.utils.ComponentUtils;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@PageTitle("Register")
@Getter
@Setter
@Route(value = "users/register", layout = MainLayout.class)
public class UsersRegisterView extends RegisterPage<UserRegisterRequest> implements ComponentUtils,
    ValidationUtils {

    public UsersRegisterView() {
        super();
    }

    @Override
    public void initComponent() {
        super.initComponent();
        title.setText("Register");
    }

    @Override
    public void doAction() {
        textField_Email_Phone.addValueChangeListener(event -> validateFields(this, "emailPhone"));
        textField_First_Name.addValueChangeListener(event -> validateFields(this, "firstName"));
        textField_Last_Name.addValueChangeListener(event -> validateFields(this, "lastName"));
        textField_Password.addValueChangeListener(event -> validateFields(this, "password"));
        textField_Confirmed_Password.addValueChangeListener(
            event -> validateFields(this, "confirmedPassword"));
        textField_Address.addValueChangeListener(event -> validateFields(this, "address"));
        datePicker_Birthday.addValueChangeListener(event -> validateFields(this, "birthday"));
        select_G.addValueChangeListener(event -> validateFields(this, "gender"));

        button_Save.addClickListener(event -> {
            validateAllFields();
            if(isCheckedBox()){
                if (isFormValid()) {
                    try {
                        HttpResponse<String> response = ApiUtils.postRequest(
                            ApiConstant.BASE_URL_BE + ApiConstant.API_PATCH + "/users/register", fetchData(new UserRegisterRequest(), new HashMap<>()));
                        Dialog dialog;
                        switch (response.statusCode()) {
                            case 200:
                                dialog = new Dialog();
                                dialog.add(new H3("Register successfully"));
                                dialog.open();
                                break;
                            case 400:
                                dialog = new Dialog();
                                dialog.add(new H3("Account already exists, please try again with another account"));
                                dialog.open();
                                break;
                            default:
                                dialog = new Dialog();
                                dialog.add(new H3("An error occurred while creating a new user"));
                                dialog.open();
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println(
                            "An error occurred while creating a new user: " + e.getMessage());
                    }
                }
            }else{
                checkbox.setErrorMessage("You must accept the terms and conditions");
                checkbox.setInvalid(true);
            }
        });
    }

    @Override
    public Map<String, Object> fetchData(UserRegisterRequest user, Map<String, Object> payload) {
        String email_phone = textField_Email_Phone.getValue();
        LocalDateTime birthday = datePicker_Birthday.getValue().atStartOfDay();

        user.setId(-1L);
        user.setFirstName(textField_First_Name.getValue());
        user.setLastName(textField_Last_Name.getValue());
        if (checkTypeAccount(email_phone)) {
            user.setEmail(email_phone);
            user.setPhone(null);
        } else {
            user.setEmail(null);
            user.setPhone(email_phone);
        }
        user.setPassword(textField_Password.getValue());
        user.setAddress(textField_Address.getValue());
        user.setBirthday(birthday.toString());
        user.setGender(
            StringUtils.convertStringVisualToEnum(select_G.getValue()).get(select_G.getValue()));
        user.setRole(new Role(0, UserRoleEnum.USER));
        user.setStatus(new Status(0, UserStatusEnum.UNVERIFIED));
        user.setCreated_at(LocalDate.now().toString());
        user.setUpdated_at(LocalDate.now().toString());
        user.setAvatar_url(null);
        user.setGoogle_account_id(0);
        user.setFacebook_account_id(0);

        return PayloadUtils.generatePayloadUser(payload, user);
    }

    private void validateAllFields() {
        validateFields(this,"emailPhone");
        validateFields(this,"firstName");
        validateFields(this,"lastName");
        validateFields(this,"password");
        validateFields(this,"confirmedPassword");
        validateFields(this,"address");
        validateFields(this,"birthday");
        validateFields(this,"gender");
    }

    private boolean isFormValid() {
        return !textField_Email_Phone.isInvalid() &&
            !textField_First_Name.isInvalid() &&
            !textField_Last_Name.isInvalid() &&
            !textField_Password.isInvalid() &&
            !textField_Confirmed_Password.isInvalid() &&
            !textField_Address.isInvalid() &&
            (datePicker_Birthday.getValue() != null) &&
            (select_G.getValue() != null);
    }

    private boolean isCheckedBox(){
        return checkbox.getValue();
    }

    @Override
    public void checkTestAccount(String email_phone, String password) {

    }

    record SampleItem(String value, String label, Boolean disabled) {

    }

    private void setSelectSampleData(Select select) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("state1", "State 1", null));
        sampleItems.add(new SampleItem("state2", "State 2", null));
        sampleItems.add(new SampleItem("state3", "State 3", null));
        select.setItems(sampleItems);
        select.setItemLabelGenerator(item -> ((SampleItem) item).label());
        select.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}
