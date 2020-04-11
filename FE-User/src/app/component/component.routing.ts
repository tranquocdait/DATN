import { Routes } from '@angular/router';
import { ListItemComponent } from './list-item/list-item.component';
import { ListPostComponent } from './list-post/list-post.component';

export const ComponentsRoutes: Routes = [
	{
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
		]
	}
];
