// Generated code from Butter Knife. Do not modify!
package com.example.hyun.drawnav;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target, View source) {
    this.target = target;

    target.id = Utils.findRequiredViewAsType(source, R.id.idInput, "field 'id'", EditText.class);
    target.nickname = Utils.findRequiredViewAsType(source, R.id.nickname, "field 'nickname'", EditText.class);
    target.pwd = Utils.findRequiredViewAsType(source, R.id.pwd, "field 'pwd'", EditText.class);
    target.pwdSure = Utils.findRequiredViewAsType(source, R.id.pwdSure, "field 'pwdSure'", EditText.class);
    target.genMale = Utils.findRequiredViewAsType(source, R.id.genderMale, "field 'genMale'", RadioButton.class);
    target.genFemale = Utils.findRequiredViewAsType(source, R.id.genderFemale, "field 'genFemale'", RadioButton.class);
    target.ageSpinner = Utils.findRequiredViewAsType(source, R.id.ageSpinner, "field 'ageSpinner'", Spinner.class);
    target.listOfSpinner = Utils.findRequiredViewAsType(source, R.id.listOfSpinner, "field 'listOfSpinner'", TextView.class);
    target.regBtn = Utils.findRequiredViewAsType(source, R.id.regBtn, "field 'regBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.id = null;
    target.nickname = null;
    target.pwd = null;
    target.pwdSure = null;
    target.genMale = null;
    target.genFemale = null;
    target.ageSpinner = null;
    target.listOfSpinner = null;
    target.regBtn = null;
  }
}
