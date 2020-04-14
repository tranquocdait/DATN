import { Routes } from '@angular/router';
import { ListItemComponent } from './list-item/list-item.component';
import { ListPostComponent } from './list-post/list-post.component';
import { ItemInfoComponent } from './item-info/item-info.component';
import { ConfirmPurchaseComponent } from './confirm-purchase/confirm-purchase.component';

export const ComponentsRoutes: Routes = [{
	path: '',
	children: [
		{
			path: 'list-item',
			component: ListItemComponent,
			pathMatch: 'full'
		},
		{
			path: 'list-post',
			component: ListPostComponent
		},
		{
			path: 'item-info',
			component: ItemInfoComponent,
			pathMatch: 'full'
		},
		{
			path: 'confirm-purchase',
			component: ConfirmPurchaseComponent,
			pathMatch: 'full'
		}
	]
}];
