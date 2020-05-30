/*
 * Copyright 2018-2020 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetpack_java.sample_05_navigation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpack_java.R;
import com.example.jetpack_java.common_data.Configs;
import com.example.jetpack_java.common_data.bean.Moment;
import com.example.jetpack_java.databinding.AdapterMomentDatabindingBinding;
import com.example.jetpack_java.databinding.FragmentListNavigationBinding;
import com.example.jetpack_java.sample_04_databinding.ui.state.ListViewModel;
import com.kunminx.architecture.ui.BaseFragment;
import com.kunminx.architecture.ui.adapter.SimpleBindingAdapter;

/**
 * Create by KunMinX at 2020/5/30
 */
public class ListFragment_Navigation extends BaseFragment {

    private ListViewModel mListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListViewModel = getFragmentViewModel(ListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_navigation, container, false);
        FragmentListNavigationBinding binding = FragmentListNavigationBinding.bind(view);
        binding.setLifecycleOwner(this);
        binding.setVm(mListViewModel);
        binding.setClick(new ClickProxy());
        binding.setAdapter(new SimpleBindingAdapter<Moment, AdapterMomentDatabindingBinding>(mActivity.getApplicationContext(), R.layout.adapter_moment_databinding) {
            @Override
            protected void onSimpleBindItem(AdapterMomentDatabindingBinding binding, Moment moment, RecyclerView.ViewHolder holder) {
                binding.setMoment(moment);
                binding.getRoot().setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Configs.THIS_MOMENT, moment);
                    nav().navigate(R.id.action_listFragment_Navigation_to_detailFragment_Navigation, bundle);
                });
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), moments -> {
            mListViewModel.list.setValue(moments);
        });

        mListViewModel.requestList();
    }

    public class ClickProxy {
        public void fabClick() {
            nav().navigate(R.id.action_listFragment_Navigation_to_editorFragment_Navigation);
        }
    }
}
