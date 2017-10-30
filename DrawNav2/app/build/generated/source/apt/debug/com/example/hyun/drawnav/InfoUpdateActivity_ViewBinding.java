// Generated code from Butter Knife. Do not modify!
package com.example.hyun.drawnav;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoUpdateActivity_ViewBinding implements Unbinder {
  private InfoUpdateActivity target;

  @UiThread
  public InfoUpdateActivity_ViewBinding(InfoUpdateActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InfoUpdateActivity_ViewBinding(InfoUpdateActivity target, View source) {
    this.target = target;

    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profileImage, "field 'profileImage'", ImageView.class);
    target.nicknameView = Utils.findRequiredViewAsType(source, R.id.nicknameView, "field 'nicknameView'", TextView.class);
    target.idView = Utils.findRequiredViewAsType(source, R.id.idView, "field 'idView'", TextView.class);
    target.pwdToChange = Utils.findRequiredViewAsType(source, R.id.pwdToChange, "field 'pwdToChange'", EditText.class);
    target.pwdToSure = Utils.findRequiredViewAsType(source, R.id.pwdToSure, "field 'pwdToSure'", EditText.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radioGroup, "field 'radioGroup'", RadioGroup.class);
    target.genMale = Utils.findRequiredViewAsType(source, R.id.genderMale, "field 'genMale'", RadioButton.class);
    target.genFemale = Utils.findRequiredViewAsType(source, R.id.genderFemale, "field 'genFemale'", RadioButton.class);
    target.ageSpinner = Utils.findRequiredViewAsType(source, R.id.ageSpinner, "field 'ageSpinner'", Spinner.class);
    target.listOfSpinner = Utils.findRequiredViewAsType(source, R.id.listOfSpinner, "field 'listOfSpinner'", TextView.class);
    target.saveBtn = Utils.findRequiredViewAsType(source, R.id.saveButton, "field 'saveBtn'", Button.class);
    target.cancelBtn = Utils.findRequiredViewAsType(source, R.id.cancelButton, "field 'cancelBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoUpdateActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profileImage = null;
    target.nicknameView = null;
    target.idView = null;
    target.pwdToChange = null;
    target.pwdToSure = null;
    target.radioGroup = null;
    target.genMale = null;
    target.genFemale = null;
    target.ageSpinner = null;
    target.listOfSpinner = null;
    target.saveBtn = null;
    target.cancelBtn = null;
  }
}
