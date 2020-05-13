import { NgModule } from '@angular/core';
import { ConvertAmountPipe } from './convert-amount.pipe';
import { ConvertStringPipe } from './convert-string.pipe';
import { TranslationStringPipe } from './translation-string.pipe';


@NgModule({
    imports: [
    ],
    exports: [
        ConvertAmountPipe,
        ConvertStringPipe,
        TranslationStringPipe
    ],
    declarations: [
        ConvertAmountPipe,
        ConvertStringPipe,
        TranslationStringPipe
    ],
    entryComponents: [
    ]
})
export class PipeModule {

}
