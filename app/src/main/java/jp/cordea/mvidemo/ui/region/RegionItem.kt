package jp.cordea.mvidemo.ui.region

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.mvidemo.R
import jp.cordea.mvidemo.databinding.ListItemRegionBinding
import javax.inject.Inject

class RegionItem @Inject constructor(
) : BindableItem<ListItemRegionBinding>() {

    private lateinit var viewModel: RegionItemViewModel

    fun setViewModel(viewModel: RegionItemViewModel) = apply {
        this.viewModel = viewModel
    }

    override fun getLayout(): Int =
            R.layout.list_item_region

    override fun bind(viewBinding: ListItemRegionBinding, position: Int) {
        viewBinding.vm = viewModel
        viewBinding.root.setOnClickListener {
        }
    }
}
