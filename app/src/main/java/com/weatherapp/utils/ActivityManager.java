package com.weatherapp.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.weatherapp.R;

import java.util.ArrayList;

public class ActivityManager<T> {

    private FragmentActivity currentActivity;
    private Dialog loaderDialogView = null;

    public void setCurrentActivity(FragmentActivity fragmentActivity) {
        this.currentActivity = fragmentActivity;
    }

    public FragmentActivity getCurrentActivity() {
        return this.currentActivity;
    }

    public <T> void goTo(
            Class<T> classTo,
            @Nullable Bundle bundle,
            @Nullable ArrayList<Integer> flags
    ) {
        Intent intent = new Intent(currentActivity, classTo);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (flags != null) {
            for (Integer flag : flags) {
                intent.addFlags(flag);
            }
        }
        currentActivity.startActivity(intent);
    }

    public void goToFragment(
            Fragment fragmentTo,
            Integer container,
            @Nullable Bundle bundle,
            Boolean addNewTransaction,
            Boolean addToBackStack
    ) {
        if (bundle != null) {
            fragmentTo.setArguments(bundle);
        }

        FragmentTransaction currentFragmentTransaction = currentActivity.getSupportFragmentManager().beginTransaction();

        // Check if backStack is required
        if (addToBackStack) {
            currentFragmentTransaction.addToBackStack(fragmentTo.getTag());
        }
        // Check if add / replace case
        if (addNewTransaction) {
            currentFragmentTransaction.add(container, fragmentTo);
        } else {
            currentFragmentTransaction.replace(container, fragmentTo);
        }
        // Verify stateSaved for supportFragmentManager
        if (currentActivity.getSupportFragmentManager().isStateSaved()) {
            currentFragmentTransaction.commitAllowingStateLoss();
        } else {
            currentFragmentTransaction.commit();
        }
    }

    public void showLoader() {
        if (loaderDialogView == null) {
            loaderDialogView = new Dialog(currentActivity);
            loaderDialogView.setContentView(R.layout.layout_loader);
            Animator animator = AnimatorInflater.loadAnimator(currentActivity, R.animator.flipping);
            animator.setTarget(loaderDialogView.findViewById(R.id.imgSpinnerIcon));
            animator.setDuration(700);
            animator.start();
            loaderDialogView.setCancelable(false);
            loaderDialogView.show();
            if (loaderDialogView.getWindow() != null) {
                loaderDialogView.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                loaderDialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

        }
    }

    public void hideLoader() {
        loaderDialogView.dismiss();
        loaderDialogView = null;
    }
}
