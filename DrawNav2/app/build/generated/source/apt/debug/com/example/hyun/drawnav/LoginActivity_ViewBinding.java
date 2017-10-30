// Generated code from Butter Knife. Do not modify!
package com.example.hyun.drawnav;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.id = Utils.findRequiredViewAsType(source, R.id.idInput, "field 'id'", EditText.class);
    target.pwd = Utils.findRequiredViewAsType(source, R.id.pwd, "field 'pwd'", EditText.class);
    target.loginBtn = Utils.findRequiredViewAsType(source, R.id.loginBtn, "field 'loginBtn'", Button.class);
    target.linkToReg = Utils.findRequiredViewAsType(source, R.id.linkToRegister, "field 'linkToReg'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.id = null;
    target.pwd = null;
    target.loginBtn = null;
    target.linkToReg = null;
  }
}
