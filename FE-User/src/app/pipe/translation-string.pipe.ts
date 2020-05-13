import { Pipe, PipeTransform } from '@angular/core';
import { TranslationKey } from './translation-key';

@Pipe({
  name: 'translationString'
})
export class TranslationStringPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    TranslationKey.forEach(element => {
      if (element.key === value) {
        return element.value;
      }
    });
    return '';
  }

}
