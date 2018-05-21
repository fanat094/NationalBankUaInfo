package com.yamschikov.dima.nationalbankuainfo;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

public class BaseFragmentManager {
    private static final int EMPTY_FRAGMENT_STACK_SIZE = 1;
    private BaseFragment mCurrentFragment;
    private Stack<BaseFragment> mFragmentStack = new Stack();

    public void setFragment(AppCompatActivity activity, BaseFragment fragment, @IdRes int containerId, boolean addToBackStack) {
        if (activity != null && !activity.isFinishing() && !isAlreadyContains(fragment)) {
            FragmentTransaction fragmentTransaction = createAddTransaction(activity, fragment, addToBackStack);
            fragmentTransaction.replace(containerId, fragment);
            commitAddTransaction(activity, fragment, fragmentTransaction, addToBackStack);
        }
    }

    public void setFragment(AppCompatActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if (activity != null && !activity.isFinishing() && !isAlreadyContains(fragment)) {
            FragmentTransaction fragmentTransaction = createAddTransaction(activity, fragment, true);
            fragmentTransaction.replace(containerId, fragment);
            commitAddTransaction(activity, fragment, fragmentTransaction, true);
        }
    }

    public void addFragment(AppCompatActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if (activity != null && !activity.isFinishing() && !isAlreadyContains(fragment)) {
            FragmentTransaction fragmentTransaction = createAddTransaction(activity, fragment, true);
            fragmentTransaction.add(containerId, fragment);
            commitAddTransaction(activity, fragment, fragmentTransaction, true);
        }
    }

    public boolean removeCurrentFragment(AppCompatActivity activity) {
        return removeFragment(activity, this.mCurrentFragment);
    }

    public boolean removeFragment(AppCompatActivity activity, BaseFragment fragment) {
        boolean canRemove = true;
        if (fragment == null || this.mFragmentStack.size() <= EMPTY_FRAGMENT_STACK_SIZE) {
            canRemove = false;
        }
        if (canRemove) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            this.mFragmentStack.pop();
            this.mCurrentFragment = (BaseFragment) this.mFragmentStack.lastElement();
            transaction.remove(fragment);
            commitTransaction(activity, transaction);
        }
        return canRemove;
    }

    private FragmentTransaction createAddTransaction(AppCompatActivity activity, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        return fragmentTransaction;
    }

    private void commitAddTransaction(AppCompatActivity activity, BaseFragment fragment, FragmentTransaction transaction, boolean addToBackStack) {
        if (transaction != null) {
            this.mCurrentFragment = fragment;
            if (!addToBackStack) {
                this.mFragmentStack = new Stack();
            }
            this.mFragmentStack.add(fragment);
            commitTransaction(activity, transaction);
        }
    }

    private void commitTransaction(AppCompatActivity activity, FragmentTransaction transaction) {
        transaction.commit();
        this.mCurrentFragment.onScreen();
    }

    public boolean isAlreadyContains(BaseFragment fragment) {
        if (fragment == null || this.mCurrentFragment == null || !this.mCurrentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            return false;
        }
        return true;
    }

    public Stack<BaseFragment> getFragmentStack() {
        return this.mFragmentStack;
    }
}
